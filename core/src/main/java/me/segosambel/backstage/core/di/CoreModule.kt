package me.segosambel.backstage.core.di

import androidx.room.Room
import me.segosambel.backstage.core.BuildConfig
import me.segosambel.backstage.core.data.MovieRepositoryImpl
import me.segosambel.backstage.core.data.source.local.LocalDataSource
import me.segosambel.backstage.core.data.source.local.room.BackstageDatabase
import me.segosambel.backstage.core.data.source.remote.RemoteDataSource
import me.segosambel.backstage.core.data.source.remote.network.ApiService
import me.segosambel.backstage.core.data.source.remote.network.KeyInterceptor
import me.segosambel.backstage.core.domain.repository.MovieRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<BackstageDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("backstage".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), BackstageDatabase::class.java, "backstage_database.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val hostname = BuildConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog=")
            .add(hostname, "sha256/vxRon/El5KuI4vx5ey1DgmsYmRY0nDd5Cg4GfJ8S+bg=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(KeyInterceptor())
            .addInterceptor(loggingInterceptor)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}