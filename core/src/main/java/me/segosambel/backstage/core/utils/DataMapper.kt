package me.segosambel.backstage.core.utils

import me.segosambel.backstage.core.data.source.local.entity.MovieEntity
import me.segosambel.backstage.core.data.source.remote.response.MovieResponse
import me.segosambel.backstage.core.domain.model.Movie

object DataMapper {
    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                movieId = it.movieId,
                title = it.title,
                overview = it.overview,
                posterUrl = it.posterUrl,
                backdropUrl = it.backdropUrl,
                rating = it.rating,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id,
                title = it.title,
                overview = it.overview,
                posterUrl = it.posterPath,
                backdropUrl = it.backdropPath,
                rating = it.voteAverage,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapDomainToEntity(input: Movie): MovieEntity {
        return MovieEntity(
            movieId = input.movieId,
            title = input.title,
            overview = input.overview,
            posterUrl = input.posterUrl,
            backdropUrl = input.backdropUrl,
            rating = input.rating,
            isFavorite = input.isFavorite
        )
    }
}