package com.wrecker.razorpay.data.model

import com.google.gson.annotations.SerializedName
import com.wrecker.razorpay.domain.model.MovieModel

data class MovieResponse(
    @SerializedName("Search")
    val search: List<MovieModel>
)