package com.wrecker.razorpay.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wrecker.razorpay.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi = Retrofit
        .Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)
}