package me.segosambel.backstage.core.data.source.remote.network

import me.segosambel.backstage.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovie(): ListMovieResponse
}