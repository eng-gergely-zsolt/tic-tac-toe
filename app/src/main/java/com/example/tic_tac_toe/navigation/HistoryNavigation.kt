package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.history.HistoryScreen

fun NavGraphBuilder.historyNavigation() {
    composable<NavigationRoute.HistoryNavigationRoute> {
        HistoryScreen()
    }
}

fun NavController.navigateToHistoryScreen() {
    this.navigate(NavigationRoute.HistoryNavigationRoute)
}
