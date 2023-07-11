package com.wrecker.razorpay.domain.repository

import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun findMovieByTitle(title: String): Flow<Event<List<MovieModel>>>

    suspend fun getMovieDetails(imdbId: String): Flow<Event<MovieModel>>
}