package com.example.tic_tac_toe

import android.app.Application
import com.example.tic_tac_toe.di.DataModule
import com.example.tic_tac_toe.di.DatabaseModule
import com.example.tic_tac_toe.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TicTacToeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            // Enable logging for Koin in Android's logging system.
            androidLogger()

            // Provide application context to Koin.
            androidContext(this@TicTacToeApplication)

            // Load and register the dependency modules with Koin.
            modules(DatabaseModule, DataModule, ViewModelModule)
        }
    }
}
