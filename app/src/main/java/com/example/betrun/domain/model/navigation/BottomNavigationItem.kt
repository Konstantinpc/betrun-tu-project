package com.example.betrun.domain.model.navigation

import com.example.betrun.R
import com.example.betrun.util.navigation.Destination

data class BottomNavigationItem(
    val label : String = "",
    val icon : Int = 0,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Program",
                icon = R.drawable.calendar,
                route = Destination.Program.route
            ),
            BottomNavigationItem(
                label = "You",
                icon = R.drawable.user,
                route = Destination.Profile.route
            )
        )
    }
}