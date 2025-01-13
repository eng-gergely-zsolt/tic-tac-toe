package com.example.tic_tac_toe.di

import com.example.presentation.TicTacToeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { TicTacToeViewModel() }
}
