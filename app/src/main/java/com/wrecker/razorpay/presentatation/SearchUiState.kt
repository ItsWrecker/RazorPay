package com.wrecker.razorpay.presentatation

import com.wrecker.razorpay.domain.model.MovieModel

data class SearchUiState(
    val error: String?=null,
    val isLoading: Boolean = false,
    val movieList: List<MovieModel> = emptyList()
)