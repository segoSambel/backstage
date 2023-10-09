package me.segosambel.backstage

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import me.segosambel.backstage.core.di.databaseModule
import me.segosambel.backstage.core.di.networkModule
import me.segosambel.backstage.core.di.repositoryModule
import me.segosambel.backstage.di.useCaseModule
import me.segosambel.backstage.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BackstageApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BackstageApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }

        val preference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preference.getBoolean(
            getString(R.string.pref_key_dark_mode),
            false
        ).apply {
            if (this) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}