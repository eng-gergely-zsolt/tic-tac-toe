package com.example.presentation.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Field
import com.example.core.domain.model.Player
import com.example.core.domain.model.TakenField
import com.example.core.domain.model.TicTacToe
import com.example.core.domain.repository.TicTacToeDbRepository
import com.example.presentation.game.TicTacToeContract.TicTacToeState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

    private val animationMap: MutableMap<Int, Boolean> = mutableMapOf()

    private var _state by mutableStateOf(
        TicTacToeState(
            draws = 0,
            hostWins = 0,
            guestWins = 0,
            winner = null,
            isDraw = false,
            board = mutableSetOf(),
            showEndGameDialog = false,
            borderMap = mutableMapOf(),
            currentPlayer = Player.Host
        )
    )

    val state: TicTacToeState get() = _state

    private var startDateTime: LocalDateTime = LocalDateTime.now().withSecond(0).withNano(0)

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    /**
     * Dismisses the dialog.
     */
    fun dismissDialog() {
        _state = _state.copy(
            winner = null,
            isDraw = false,
            showEndGameDialog = false
        )
    }

    /**
     * Resets the game for a rematch.
     */
    fun replay() {
        takenMoves = 0

        animationMap.clear()

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        _state = _state.copy(
            winner = null,
            isDraw = false,
            board = mutableSetOf(),
            showEndGameDialog = false
        )
    }

    /**
     * Resets the game if the users want to restart the game without finishing it.
     */
    fun resetBoard() {
        takenMoves = 0

        animationMap.clear()

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        _state = _state.copy(
            board = mutableSetOf()
        )
    }

    /**
     * Initialize the board fo the game to start.
     */
    fun initializeBoard(boardSize: Int) {
        val borderMap: MutableMap<Int, Pair<Boolean, Boolean>> = mutableMapOf()

        this.boardSize = boardSize

        magicSquare = Array(boardSize) { IntArray(boardSize) }

        resultSquare = Array(boardSize) { Array(boardSize) { TakenField() } }

        if (boardSize % 2 == 1) {
            generateOddMagicSquare()

        } else {
            generateDoublyEvenMagicSquare()
        }

        for (i in 0 until boardSize * boardSize) {
            val leftBorder = i % boardSize != 0

            val bottomBorder = i + 1 <= boardSize * (boardSize - 1)

            borderMap[i] = Pair(leftBorder, bottomBorder)
        }

        _state = _state.copy(
            borderMap = borderMap
        )
    }

    /**
     * Checks whether it's necessary to animate the icon or not. We animate them only once when it's first placed.
     */
    fun shouldAnimate(indexOfField: Int): Boolean {
        val result = animationMap[indexOfField] == false

        if (!result) {
            animationMap[indexOfField] = true
        }

        return result
    }

    /**
     * Sets the game variables when a field has been occupied.
     */
    fun takeField(indexOfField: Int) {
        val lastTakenField = Field(
            index = indexOfField,
            player = _state.currentPlayer
        )

        val updatedBoard = _state.board

        if (updatedBoard.find { it.index == indexOfField } == null) {
            val currentPlayer = _state.currentPlayer

            ++takenMoves

            updateResulSquare(indexOfField)

            updatedBoard.add(lastTakenField)

            animationMap[indexOfField] = false

            val updatedPlayer = updateCurrentPlayer()

            val updatedEndGame = checkEndGame(currentPlayer)

            _state = _state.copy(
                board = updatedBoard,
                draws = updatedEndGame.draws,
                isDraw = updatedEndGame.isDraw,
                winner = updatedEndGame.winner,
                hostWins = updatedEndGame.hostWins,
                guestWins = updatedEndGame.guestWins,
                currentPlayer = updatedPlayer.currentPlayer,
            )
        }
    }

    /**
     * Updates the result array when a field has been occupied.
     */
    private fun updateResulSquare(indexOfField: Int) {
        val boardRowIndex = indexOfField / boardSize

        val boardColumnIndex = indexOfField % boardSize

        resultSquare[boardColumnIndex][boardRowIndex].player = _state.currentPlayer

        resultSquare[boardColumnIndex][boardRowIndex].magicSquareValue =
            magicSquare[boardColumnIndex][boardRowIndex]
    }

    /**
     * Update the current play after every turn.
     */
    private fun updateCurrentPlayer(): TicTacToeState {
        return if (_state.currentPlayer == Player.Host) {
            _state.copy(
                currentPlayer = Player.Guest
            )
        } else {
            _state.copy(
                currentPlayer = Player.Host
            )
        }
    }

    /**
     * Check whether a game is over or not. (Has been won or is a draw.)
     */
    private fun checkEndGame(currentPlayer: Player): TicTacToeState {
        val updatedEndGame = if (hasPlayerWon(currentPlayer)) {
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
            return _state
        }

        saveTicTacToeMatch()

        return updatedEndGame
    }

    /**
     * Checks whether the current play has won the match with the last move or not.
     */
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

    /**
     * Saves the match in the database.
     */
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                { error ->
                    Log.d("Error", error.message.toString())
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
