package me.segosambel.backstage.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.segosambel.backstage.core.data.source.local.LocalDataSource
import me.segosambel.backstage.core.data.source.remote.RemoteDataSource
import me.segosambel.backstage.core.data.source.remote.network.ApiResponse
import me.segosambel.backstage.core.data.source.remote.response.MovieResponse
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.repository.MovieRepository
import me.segosambel.backstage.core.utils.DataMapper

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {
    override fun getMovies() =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies().map(DataMapper::mapEntitiesToDomain)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val moviesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovies(moviesList)
            }
        }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map(DataMapper::mapEntitiesToDomain)
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavorite(movieEntity, state)
        }
    }
}