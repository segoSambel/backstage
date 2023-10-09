package me.segosambel.backstage.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.segosambel.backstage.core.data.Resource
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = movieRepository.getMovies()
}