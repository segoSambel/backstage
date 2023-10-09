package me.segosambel.backstage.detail

import androidx.lifecycle.ViewModel
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.usecase.SetFavoriteMovieUseCase

class DetailViewModel(
    private val setFavoriteMovieUseCase: SetFavoriteMovieUseCase
) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, state: Boolean) = setFavoriteMovieUseCase(movie, state)
}