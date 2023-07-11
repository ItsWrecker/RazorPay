package com.wrecker.razorpay.domain.interactor

interface BaseUseCase<in R, out T> {
    suspend operator fun invoke(prams: R): T
}