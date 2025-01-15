package com.example.tic_tac_toe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.LandingNavigationRoute
    ) {
        historyNavigation()

        landingNavigation(navController = navController)

        ticTacToeNavigation(navController = navController)
    }
}
