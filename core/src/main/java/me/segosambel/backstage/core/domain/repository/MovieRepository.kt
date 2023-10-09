package me.segosambel.backstage.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.segosambel.backstage.core.data.Resource
import me.segosambel.backstage.core.domain.model.Movie

interface MovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}