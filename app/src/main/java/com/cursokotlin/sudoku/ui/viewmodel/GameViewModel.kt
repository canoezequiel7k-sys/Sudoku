package com.cursokotlin.sudoku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.sudoku.domain.SudokuCell
import com.cursokotlin.sudoku.domain.generateSudokuGame
import com.cursokotlin.sudoku.domain.validateBoard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val board: List<List<SudokuCell>> = emptyList(),
    val solution: List<List<Int>> = emptyList(),
    val selectedCell: Pair<Int, Int>? = null,
    val lives: Int = 3,
    val seconds: Int = 0,
    val isPaused: Boolean = false,
    val undoCount: Int = 3,
    val hintCount: Int = 3,
    val history: List<List<List<SudokuCell>>> = emptyList(),
    val isGameOver: Boolean = false,
    val isYouWin: Boolean = false,
    val difficulty: String = "Normal"
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var isGameInitialized = false

    init {
        startTimer()
    }

    fun initGame(difficulty: String) {
        if (isGameInitialized) return
        isGameInitialized = true

        val gameData = generateSudokuGame(difficulty)
        _uiState.update {
            it.copy(
                board = gameData.board,
                solution = gameData.solution,
                difficulty = difficulty
            )
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                val currentState = _uiState.value
                if (!currentState.isPaused && !currentState.isGameOver && !currentState.isYouWin && isGameInitialized) {
                    _uiState.update { it.copy(seconds = it.seconds + 1) }
                }
            }
        }
    }

    fun onCellSelected(row: Int, col: Int) {
        val currentBoard = _uiState.value.board
        if (currentBoard.isEmpty()) return

        val clickedNumber = currentBoard[row][col].number

        val updatedBoard = currentBoard.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                cell.copy(
                    isSelected = (r == row && c == col),
                    isSameNumber = (clickedNumber != null && cell.number == clickedNumber)
                )
            }
        }

        _uiState.update {
            it.copy(
                selectedCell = Pair(row, col),
                board = updatedBoard
            )
        }
    }

    fun onNumberEntered(number: Int) {
        val state = _uiState.value
        val (r, c) = state.selectedCell ?: return
        if (state.isGameOver || state.isYouWin) return

        val newHistory = state.history + listOf(state.board)
        val updatedBoard = validateBoard(
            board = state.board.mapIndexed { rowIndex, rowList ->
                rowList.mapIndexed { colIndex, cell ->
                    if (rowIndex == r && colIndex == c) {
                        cell.copy(number = number)
                    } else {
                        cell
                    }
                }
            },
            solution = state.solution
        )

        var newLives = state.lives
        var isGameOver = state.isGameOver
        if (updatedBoard[r][c].hasError) {
            newLives--
            if (newLives <= 0) {
                isGameOver = true
            }
        }

        val isBoardFull = updatedBoard.all { row -> row.all { cell -> cell.number != null } }
        val hasNoErrors = updatedBoard.all { row -> row.all { cell -> !cell.hasError } }
        val isYouWin = isBoardFull && hasNoErrors

        _uiState.update {
            it.copy(
                board = updatedBoard,
                history = newHistory,
                lives = newLives,
                isGameOver = isGameOver,
                isYouWin = isYouWin
            )
        }
    }

    fun onErase() {
        val state = _uiState.value
        val (r, c) = state.selectedCell ?: return

        if (!state.board[r][c].isGiven) {
            val newHistory = state.history + listOf(state.board)
            val updatedBoard = validateBoard(
                board = state.board.mapIndexed { rowIndex, rowList ->
                    rowList.mapIndexed { colIndex, cell ->
                        if (rowIndex == r && colIndex == c) {
                            cell.copy(number = null)
                        } else {
                            cell
                        }
                    }
                },
                solution = state.solution
            )

            _uiState.update {
                it.copy(
                    board = updatedBoard,
                    history = newHistory
                )
            }
        }
    }

    fun onUndo() {
        val state = _uiState.value
        if (state.undoCount > 0 && state.history.isNotEmpty()) {
            val lastBoard = state.history.last()
            _uiState.update {
                it.copy(
                    board = lastBoard,
                    history = it.history.dropLast(1),
                    undoCount = it.undoCount - 1
                )
            }
        }
    }

    fun onHint() {
        val state = _uiState.value
        if (state.hintCount <= 0) return

        var foundRow = -1
        var foundCol = -1

        for (r in state.board.indices) {
            for (c in state.board[r].indices) {
                if (state.board[r][c].number == null) {
                    foundRow = r
                    foundCol = c
                    break
                }
            }
            if (foundRow != -1) break
        }

        if (foundRow != -1 && foundCol != -1) {
            val correctNumber = state.solution[foundRow][foundCol]
            val newHistory = state.history + listOf(state.board)
            val updatedBoard = validateBoard(
                board = state.board.mapIndexed { rowIndex, rowList ->
                    rowList.mapIndexed { colIndex, cell ->
                        if (rowIndex == foundRow && colIndex == foundCol) {
                            cell.copy(number = correctNumber)
                        } else {
                            cell
                        }
                    }
                },
                solution = state.solution
            )

            val isBoardFull = updatedBoard.all { row -> row.all { cell -> cell.number != null } }
            val hasNoErrors = updatedBoard.all { row -> row.all { cell -> !cell.hasError } }

            _uiState.update {
                it.copy(
                    board = updatedBoard,
                    history = newHistory,
                    hintCount = it.hintCount - 1,
                    isYouWin = isBoardFull && hasNoErrors
                )
            }
        }
    }

    fun onTogglePause() {
        _uiState.update { it.copy(isPaused = !it.isPaused) }
    }
}