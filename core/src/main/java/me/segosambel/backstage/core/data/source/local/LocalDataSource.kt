package me.segosambel.backstage.core.data.source.local

import kotlinx.coroutines.flow.Flow
import me.segosambel.backstage.core.data.source.local.entity.MovieEntity
import me.segosambel.backstage.core.data.source.local.room.MovieDao

class LocalDataSource(
    private val movieDao: MovieDao
) {
    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun setFavorite(movieEntity: MovieEntity, newState: Boolean) {
        movieEntity.isFavorite = newState
        movieDao.setFavorite(movieEntity)
    }
}