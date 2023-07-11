package com.wrecker.razorpay.presentatation

import com.wrecker.razorpay.domain.model.MovieModel

data class MovieDetailsState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val movie: MovieModel? = null
)