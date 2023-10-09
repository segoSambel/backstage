package me.segosambel.backstage.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ListMovieResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<MovieResponse>,

    @field:SerializedName("total_results")
	val totalResults: Int
)