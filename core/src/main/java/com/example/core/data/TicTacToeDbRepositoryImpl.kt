package com.example.core.data

import android.util.Log
import com.example.core.domain.model.TicTacToe
import com.example.core.domain.repository.TicTacToeDbRepository
import com.example.room.dao.TicTacToeDao
import com.example.room.model.TicTacToeEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.ZoneId

class TicTacToeDbRepositoryImpl(
    private val ticTacToeDao: TicTacToeDao
) : TicTacToeDbRepository {
    override fun saveTicTacToeMatch(ticTacToe: TicTacToe): Completable {
        val ticTacToeEntity = TicTacToeEntity(
            draws = ticTacToe.draws,
            hostWins = ticTacToe.hostWins,
            guestWins = ticTacToe.guestWins,
            startDateTime = ticTacToe.startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )

        return ticTacToeDao.insertTicTacToeRx(ticTacToeEntity)
            .subscribeOn(Schedulers.io())
            .doOnError { error ->
                Log.d("Error", error.message.toString())
            }
    }
}
