package com.example.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Field
import com.example.core.domain.model.Player
import com.example.core.domain.model.TakenField
import com.example.core.domain.model.TicTacToe
import com.example.core.domain.repository.TicTacToeDbRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDateTime

class TicTacToeViewModel(
    private val ticTacToeDbRepository: TicTacToeDbRepository
) : ViewModel() {
    var boardSize = 3

    private var takenMoves: Int = 0

    private val disposables = CompositeDisposable()

    private lateinit var magicSquare: Array<IntArray>

    private lateinit var resultSquare: Array<Array<TakenField>>

    private var _state by mutableStateOf(
        TicTacToeContract.TicTacToeState(
            draws = 0,
            hostWins = 0,
            guestWins = 0,
            winner = null,
            isDraw = false,
            board = mutableSetOf(),
            showEndGameDialog = false,
            currentPlayer = Player.Host,
        )
    )

    val state: TicTacToeContract.TicTacToeState get() = _state

    private var startDateTime: LocalDateTime = LocalDateTime.now().withSecond(0).withNano(0)

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    fun dismissDialog() {
        _state = _state.copy(
            winner = null,
            isDraw = false,
            showEndGameDialog = false
        )
    }

    fun replay() {
        takenMoves = 0

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        _state = _state.copy(
            winner = null,
            isDraw = false,
            board = mutableSetOf(),
            showEndGameDialog = false
        )
    }

    fun resetBoard() {
        takenMoves = 0

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        _state = _state.copy(
            board = mutableSetOf()
        )
    }

    fun initializeBoard(boardSize: Int) {
        this.boardSize = boardSize

        magicSquare = Array(boardSize) { IntArray(boardSize) }

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        if (boardSize % 2 == 1) {
            generateOddMagicSquare()

        } else {
            generateDoublyEvenMagicSquare()
        }
    }

    fun takeField(indexOfField: Int) {
        val lastTakenField = Field(
            index = indexOfField,
            player = _state.currentPlayer
        )

        val updatedBoard = _state.board

        if (updatedBoard.find { it.index == indexOfField } == null) {
            val currentPlayer = _state.currentPlayer

            ++takenMoves

            updatedBoard.add(lastTakenField)

            _state = _state.copy(
                board = updatedBoard
            )

            updateResulSquare(indexOfField)

            updateCurrentPlayer()

            checkEndGame(currentPlayer)
        }
    }

    private fun updateResulSquare(indexOfField: Int) {
        val boardRowIndex = indexOfField / boardSize

        val boardColumnIndex = indexOfField % boardSize

        resultSquare[boardColumnIndex][boardRowIndex].player = _state.currentPlayer

        resultSquare[boardColumnIndex][boardRowIndex].magicSquareValue =
            magicSquare[boardColumnIndex][boardRowIndex]
    }

    private fun updateCurrentPlayer() {
        _state = if (_state.currentPlayer == Player.Host) {
            _state.copy(
                currentPlayer = Player.Guest
            )
        } else {
            _state.copy(
                currentPlayer = Player.Host
            )
        }
    }

    private fun checkEndGame(currentPlayer: Player) {
        _state = if (hasPlayerWon(currentPlayer)) {
            _state.copy(
                winner = currentPlayer,
                hostWins = if (currentPlayer == Player.Host) _state.hostWins + 1 else _state.hostWins,
                guestWins = if (currentPlayer == Player.Guest) _state.guestWins + 1 else _state.guestWins,
            )
        } else if (takenMoves == boardSize * boardSize) {
            _state.copy(
                isDraw = true,
                draws = _state.draws + 1
            )
        } else {
            return
        }

        saveTicTacToeMatch()
    }

    private fun hasPlayerWon(currentPlayer: Player): Boolean {
        // Check rows
        for (i in 0 until boardSize) {
            if ((0 until boardSize).all { col -> resultSquare[i][col].player == currentPlayer })
                return true
        }

        // Check columns
        for (j in 0 until boardSize) {
            if ((0 until boardSize).all { row -> resultSquare[row][j].player == currentPlayer })
                return true
        }

        // Check primary diagonal
        if ((0 until boardSize).all { i -> resultSquare[i][i].player == currentPlayer })
            return true

        // Check secondary diagonal
        if ((0 until boardSize).all { i -> resultSquare[i][boardSize - i - 1].player == currentPlayer })
            return true

        // If no winning condition met, return false
        return false
    }

    private fun saveTicTacToeMatch() {
        val disposable = ticTacToeDbRepository.saveTicTacToeMatch(
            TicTacToe(
                draws = _state.draws,
                hostWins = _state.hostWins,
                guestWins = _state.guestWins,
                startDateTime = startDateTime
            )
        )
            .subscribeOn(Schedulers.io())
            .subscribe(
                {

                },
                { error ->

                }
            )

        disposables.add(disposable)
    }

    /**
     * Creates a doubly even magic square. (Ex.: 4, 8, 16).
     */
    private fun generateDoublyEvenMagicSquare() {
        var num = 1

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                magicSquare[i][j] = num++
            }
        }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if ((i % 4 == j % 4) || (i % 4 + j % 4 == 3)) {
                    magicSquare[i][j] = boardSize * boardSize + 1 - magicSquare[i][j]
                }
            }
        }

        // Print magic square
        println("The Magic Square for $boardSize:")
        println("Sum of each row or column: ${boardSize * (boardSize * boardSize + 1) / 2}")
        for (row in magicSquare) {
            println(row.joinToString(" "))
        }
    }

    /**
     * Creates an odd magic square.
     */
    private fun generateOddMagicSquare() {
        var num = 1

        var i = boardSize / 2

        var j = boardSize - 1

        while (num <= boardSize * boardSize) {
            if (i == -1 && j == boardSize) {
                j = boardSize - 2
                i = 0
            } else {
                if (j == boardSize) j = 0
                if (i < 0) i = boardSize - 1
            }

            if (magicSquare[i][j] != 0) {
                j -= 2
                i++
                continue
            } else {
                magicSquare[i][j] = num++
            }

            j++
            i--
        }

        // Print magic square
        println("The Magic Square for $boardSize:")
        println("Sum of each row or column: ${boardSize * (boardSize * boardSize + 1) / 2}")
        for (row in magicSquare) {
            println(row.joinToString(" "))
        }
    }
}
