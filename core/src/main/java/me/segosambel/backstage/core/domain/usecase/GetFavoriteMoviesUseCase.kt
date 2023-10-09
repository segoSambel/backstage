package me.segosambel.backstage.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.repository.MovieRepository

class GetFavoriteMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()
}