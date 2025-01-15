package com.example.core.domain.repository

import com.example.core.domain.model.TicTacToe
import io.reactivex.rxjava3.core.Completable

interface TicTacToeDbRepository {
    fun saveTicTacToeMatch(ticTacToe: TicTacToe): Completable
}
