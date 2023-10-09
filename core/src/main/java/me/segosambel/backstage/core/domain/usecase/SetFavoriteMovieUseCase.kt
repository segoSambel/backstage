package me.segosambel.backstage.core.domain.usecase

import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.repository.MovieRepository

class SetFavoriteMovieUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}