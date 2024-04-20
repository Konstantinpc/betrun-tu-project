package com.example.betrun.util.navigation

import androidx.navigation.navDeepLink

sealed class Destination(val route: String) {

    data object Welcome : Destination("welcome")
    data object Login : Destination("login")
    data object Register : Destination("register")
    data object QuestionsProgress : Destination("questions_progress")
    data object StartRoute : Destination("start")
    data object Program : Destination("program_route")

    data object Workout : Destination("workout_route") {
        val currentRunUriPattern = "https://betrun.example.com/$route"
        val deepLinks = listOf(
            navDeepLink {
                uriPattern = currentRunUriPattern
            }
        )
    }

    data object Profile : Destination("profile_route")
}