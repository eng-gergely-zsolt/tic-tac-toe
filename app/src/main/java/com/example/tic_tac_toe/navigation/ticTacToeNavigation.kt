package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.TicTacToeScreen
import com.example.presentation.TicTacToeViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.ticTacToeNavigation(
    navController: NavController
) {
    composable<NavigationRoute.TicTacToeNavigationRoute> {
        val viewModel: TicTacToeViewModel = koinViewModel()

        TicTacToeScreen(
            viewModel = viewModel
        )
    }
}
