package com.example.tic_tac_toe.di

import com.example.presentation.game.TicTacToeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    includes(DatabaseModule)

    viewModel { TicTacToeViewModel(get()) }
}
