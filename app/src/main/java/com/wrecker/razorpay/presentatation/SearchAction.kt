package com.wrecker.razorpay.presentatation

sealed interface SearchAction{

    data class Search(val query: String): SearchAction

}