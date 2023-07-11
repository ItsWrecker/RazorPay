package com.wrecker.razorpay.domain.interactor

import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.model.MovieModel
import com.wrecker.razorpay.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
): BaseUseCase<String, Flow<Event<List<MovieModel>>>> {

    override suspend fun invoke(prams: String): Flow<Event<List<MovieModel>>> {
        return movieRepository.findMovieByTitle(prams)
    }
}