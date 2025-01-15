package com.example.tic_tac_toe.di

import androidx.room.Room
import com.example.room.TicTacToeDatabase
import com.example.room.dao.TicTacToeDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val DatabaseModule = module {
    val ticTacToeDatabaseName = "tic_tac_toe_database"

    single {
        Room.databaseBuilder(
            androidApplication(),
            TicTacToeDatabase::class.java,
            ticTacToeDatabaseName
        ).build()
    }

    // Provide
    single<TicTacToeDao> {
        get<TicTacToeDatabase>().getTicTacToeDao()
    }
}
