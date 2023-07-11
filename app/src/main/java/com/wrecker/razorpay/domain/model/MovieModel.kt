package com.wrecker.razorpay.domain.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("Title")
    val title: String,
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Poster")
    val poster: String
)