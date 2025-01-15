package com.example.tic_tac_toe.di

import com.example.core.data.TicTacToeDbRepositoryImpl
import com.example.core.domain.repository.TicTacToeDbRepository
import org.koin.dsl.module

val DataModule = module {
    includes(DatabaseModule)

    single<TicTacToeDbRepository> {
        TicTacToeDbRepositoryImpl(get())
    }
}
