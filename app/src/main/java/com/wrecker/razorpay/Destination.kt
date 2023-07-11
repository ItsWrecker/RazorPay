package com.wrecker.razorpay


sealed class Destination(val route: String) {
    object MainScreen: Destination(route = "MainScreen")
    object DetailsScreen: Destination(route = "DetailsScreen")
}