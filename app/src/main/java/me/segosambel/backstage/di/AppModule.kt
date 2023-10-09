package me.segosambel.backstage.di

import me.segosambel.backstage.core.domain.usecase.GetFavoriteMoviesUseCase
import me.segosambel.backstage.core.domain.usecase.GetMoviesUseCase
import me.segosambel.backstage.core.domain.usecase.SetFavoriteMovieUseCase
import me.segosambel.backstage.detail.DetailViewModel
import me.segosambel.backstage.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetMoviesUseCase(get()) }
    factory { GetFavoriteMoviesUseCase(get()) }
    factory { SetFavoriteMovieUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}