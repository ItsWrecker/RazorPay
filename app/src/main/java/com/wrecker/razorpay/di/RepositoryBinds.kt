package com.wrecker.razorpay.di

import com.wrecker.razorpay.data.repository.MovieRepositoryImpl
import com.wrecker.razorpay.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinds {

    @Binds
    @Singleton
    fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}