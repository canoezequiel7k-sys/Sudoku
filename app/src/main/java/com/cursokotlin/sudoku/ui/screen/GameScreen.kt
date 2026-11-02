package com.cursokotlin.sudoku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cursokotlin.sudoku.ui.composable.SudokuBoard
import com.cursokotlin.sudoku.ui.composable.SudokuKeypad
import com.cursokotlin.sudoku.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    difficulty: String = "Normal",
    viewModel: GameViewModel = viewModel()
) {
    LaunchedEffect(difficulty) {
        viewModel.initGame(difficulty)
    }

    val uiState by viewModel.uiState.collectAsState()

    val minutes = uiState.seconds / 60
    val secs = uiState.seconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, secs)

    val completedNumbers = (1..9).filter { number ->
        uiState.board.sumOf { row -> row.count { it.number == number } } >= 9
    }.toSet()

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Sudoku",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.onTogglePause() },
                            modifier = Modifier.height(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = "Pausar"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vidas: ${"❤️".repeat(uiState.lives)}",
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = timeFormatted,
                        textAlign = TextAlign.End,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                if (uiState.board.isNotEmpty()) {
                    SudokuBoard(
                        board = uiState.board,
                        onCellClick = { row, col ->
                            viewModel.onCellSelected(row, col)
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(uiState.difficulty)
                }

                Spacer(modifier = Modifier.height(28.dp))

                SudokuKeypad(
                    completedNumbers = completedNumbers,
                    onNumberClick = { number -> viewModel.onNumberEntered(number) },
                    onEreseClick = { viewModel.onErase() },
                    onUndoClick = { viewModel.onUndo() },
                    onHintClick = { viewModel.onHint() }
                )
            }
        }

        if (uiState.isGameOver) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Game Over!") },
                text = { Text("Te has quedado sin vidas. Mejor suerte la proxima") },
                confirmButton = {
                    Button(onClick = { onBack() }) {
                        Text("Volver al menu")
                    }
                }
            )
        }

        if (uiState.isYouWin) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("¡Felicidades! 🎉") },
                text = { Text("¡Has completado el Sudoku con éxito y sin errores en $timeFormatted!") },
                confirmButton = {
                    Button(onClick = { onBack() }) {
                        Text("Volver al Menú")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        onBack = {},
        difficulty = "Normal"
    )
}