package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.history.HistoryScreen
import com.example.presentation.history.HistoryViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.historyNavigation() {
    composable<NavigationRoute.HistoryNavigationRoute> {
        val viewModel: HistoryViewModel = koinViewModel()

        HistoryScreen(
            viewModel = viewModel
        )
    }
}

fun NavController.navigateToHistoryScreen() {
    this.navigate(NavigationRoute.HistoryNavigationRoute)
}
