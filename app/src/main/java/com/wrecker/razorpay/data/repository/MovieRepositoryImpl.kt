package com.wrecker.razorpay.data.repository

import com.wrecker.razorpay.data.api.MovieApi
import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.model.MovieModel
import com.wrecker.razorpay.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun findMovieByTitle(title: String): Flow<Event<List<MovieModel>>> = flow {
        emit(Event.Loading)
        try {
            val response = api.searchMovie(title)
            if (response.isSuccessful) {
                val data = response.body()
                    ?: return@flow emit(Event.Errors("Error while fetching the movie"))
                return@flow emit(Event.Success(data.search))
            }
            return@flow emit(Event.Errors("Error while fetching the movie"))
        } catch (exception: Exception) {
            return@flow emit(Event.Errors(exception.message))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetails(imdbId: String): Flow<Event<MovieModel>> = flow {
        emit(Event.Loading)
        try {
            val response = api.getMovieDetails(imdbId = imdbId)
            if (response.isSuccessful) {
                val data = response.body()
                    ?: return@flow emit(Event.Errors("Error while fetching the movie"))
                return@flow emit(Event.Success(data))
            }
            return@flow emit(Event.Errors("Error while fetching the movie"))
        } catch (exception: Exception) {
            return@flow emit(Event.Errors(exception.message))
        }
    }.flowOn(Dispatchers.IO)

}
