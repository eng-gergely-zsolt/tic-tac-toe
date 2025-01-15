package com.example.tic_tac_toe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.presentation.game.TicTacToeScreen
import com.example.presentation.game.TicTacToeSideEffect
import com.example.presentation.game.TicTacToeViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.ticTacToeNavigation(
    navController: NavController
) {
    composable<NavigationRoute.TicTacToeNavigationRoute> { stackEntry ->
        val viewModel: TicTacToeViewModel = koinViewModel()

        val route: NavigationRoute.TicTacToeNavigationRoute = stackEntry.toRoute()

        TicTacToeScreen(
            viewModel = viewModel,
            boardSize = route.boardSize,
            onNavigation = { navigationEffect ->
                when (navigationEffect) {
                    is TicTacToeSideEffect.Navigation.NavigateBack -> {
                        navController.navigateBack()
                    }
                }
            }
        )
    }
}

fun NavController.navigateBack() {
    this.popBackStack()
}
