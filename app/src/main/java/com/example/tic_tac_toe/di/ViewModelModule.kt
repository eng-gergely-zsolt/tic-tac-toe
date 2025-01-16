package com.example.tic_tac_toe.di

import com.example.presentation.game.TicTacToeViewModel
import com.example.presentation.history.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    includes(DatabaseModule)

    viewModel { HistoryViewModel(get()) }

    viewModel { TicTacToeViewModel(get()) }
}
