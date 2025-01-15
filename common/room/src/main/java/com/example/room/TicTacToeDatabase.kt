package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.dao.TicTacToeDao
import com.example.room.model.TicTacToeEntity

/**
 * Represents the tic tac toe database.
 */
@Database(entities = [TicTacToeEntity::class], version = 1, exportSchema = false)
abstract class TicTacToeDatabase : RoomDatabase() {
    abstract fun getTicTacToeDao(): TicTacToeDao
}
