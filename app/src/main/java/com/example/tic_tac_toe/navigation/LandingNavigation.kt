package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.landing.LandingScreen
import com.example.presentation.landing.LandingSideEffect

fun NavGraphBuilder.landingNavigation(
    navController: NavController
) {
    composable<NavigationRoute.LandingNavigationRoute> {
        LandingScreen(onNavigation = { navigationEffect ->
            when (navigationEffect) {
                is LandingSideEffect.Navigation.NavigateToHistory -> {
                    navController.navigateToHistoryScreen()
                }

                is LandingSideEffect.Navigation.NavigateToTicTacToe -> {
                    navController.navigateToTicTacToeScreen(navigationEffect.boardSize)
                }
            }
        })
    }
}

fun NavController.navigateToTicTacToeScreen(boardSize: Int) {
    this.navigate(NavigationRoute.TicTacToeNavigationRoute(boardSize))
}
