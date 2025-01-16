package com.example.core.domain.repository

import com.example.core.domain.model.TicTacToe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface TicTacToeDbRepository {
    /**
     * Saves tic tac toe matches in the database.
     * @param ticTacToe Represents the tic tac toe match.
     */
    fun saveTicTacToeMatch(ticTacToe: TicTacToe): Completable

    /**
     * Fetches all the tic tac toe matches from the database.
     */
    fun getTicTacToeOrderedByStartDateTime(): Maybe<List<TicTacToe>>
}
