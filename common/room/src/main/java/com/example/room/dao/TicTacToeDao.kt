package com.example.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.room.model.TicTacToeEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

/**
 * Represents the actions that can be done on the game table.
 */
@Dao
interface TicTacToeDao {
    @Upsert
    fun insertTicTacToeRx(ticTacToeEntity: TicTacToeEntity): Completable

    @Query("SELECT * FROM tic_tac_toe ORDER BY startDateTime ASC")
    fun getTicTacToeOrderedByStartDateTime(): Maybe<List<TicTacToeEntity>>
}
