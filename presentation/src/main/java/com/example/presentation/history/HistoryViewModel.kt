package com.example.presentation.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.domain.repository.TicTacToeDbRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.presentation.history.HistoryContract.HistoryState

class HistoryViewModel(
    private val ticTacToeDbRepository: TicTacToeDbRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private var _state by mutableStateOf(
        HistoryState (
            isLoading = false,
            ticTacToeMatches = listOf()
        )
    )

    val state: HistoryState get() = _state

    init {
        _state = _state.copy(
            isLoading = true
        )
        getTicTacToeHistory()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun getTicTacToeHistory() {
        val disposable = ticTacToeDbRepository.getTicTacToeOrderedByStartDateTime()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ticTacToeMatches ->
                    _state = _state.copy(
                        isLoading = false,
                        ticTacToeMatches = ticTacToeMatches
                    )
                },
                { error ->
                    Log.d("Error", error.message.toString())

                }
            )

        disposables.add(disposable)
    }
}
