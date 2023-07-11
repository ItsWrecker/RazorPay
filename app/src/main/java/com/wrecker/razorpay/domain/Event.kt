package com.wrecker.razorpay.domain

sealed interface Event <out R> {
    object Loading: Event<Nothing>
    data class Errors(val message: String?=null): Event<Nothing>
    data class Success<R>(val data: R): Event<R>
}