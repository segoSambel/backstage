package me.segosambel.backstage.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import me.segosambel.backstage.core.domain.usecase.GetFavoriteMoviesUseCase

class FavoriteViewModel(
    getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {
    val favoriteMovies by lazy { getFavoriteMoviesUseCase().asLiveData() }
}