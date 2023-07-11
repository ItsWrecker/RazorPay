package com.wrecker.razorpay.domain.interactor

import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.model.MovieModel
import com.wrecker.razorpay.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseUseCase<String, Flow<Event<MovieModel>>> {
    override suspend fun invoke(prams: String): Flow<Event<MovieModel>> {
        return movieRepository.getMovieDetails(prams)
    }
}