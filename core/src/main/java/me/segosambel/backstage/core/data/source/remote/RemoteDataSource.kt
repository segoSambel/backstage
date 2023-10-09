package me.segosambel.backstage.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.segosambel.backstage.core.data.source.remote.network.ApiResponse
import me.segosambel.backstage.core.data.source.remote.network.ApiService
import me.segosambel.backstage.core.data.source.remote.response.MovieResponse

class RemoteDataSource(
    private val apiService: ApiService
) {
    fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovie()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                Log.e("RemoteDataSource", e.stackTraceToString())
            }
        }.flowOn(Dispatchers.IO)
    }
}