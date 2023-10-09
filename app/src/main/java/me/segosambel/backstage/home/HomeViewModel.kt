package me.segosambel.backstage.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import me.segosambel.backstage.core.domain.usecase.GetMoviesUseCase

class HomeViewModel(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    val movies by lazy { getMoviesUseCase().asLiveData() }
}