package com.example.core.data

import android.util.Log
import com.example.core.domain.model.TicTacToe
import com.example.core.domain.repository.TicTacToeDbRepository
import com.example.room.dao.TicTacToeDao
import com.example.room.model.TicTacToeEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.Instant
import java.time.LocalDateTime
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

    override fun getTicTacToeOrderedByStartDateTime(): Maybe<List<TicTacToe>> {
        return ticTacToeDao.getTicTacToeOrderedByStartDateTime()
            .subscribeOn(Schedulers.io())
            .map { entities ->
                entities.map { entity ->
                    TicTacToe(
                        draws = entity.draws,
                        hostWins = entity.hostWins,
                        guestWins = entity.guestWins,
                        startDateTime = LocalDateTime
                            .ofInstant(Instant.ofEpochMilli(entity.startDateTime), ZoneId.systemDefault())
                    )
                }
            }
            .doOnError { error ->
                Log.d("Error", error.message.toString())
            }
    }
}
