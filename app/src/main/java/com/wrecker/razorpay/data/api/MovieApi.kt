package com.wrecker.razorpay.data.api

import com.wrecker.razorpay.data.model.MovieResponse
import com.wrecker.razorpay.domain.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun searchMovie(
        @Query("s")
        title: String,
        @Query("apikey")
        apikey: String = "d347b563"
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey")
        apikey: String = "d347b563",
        @Query("i")
        imdbId: String
    ): Response<MovieModel>
}