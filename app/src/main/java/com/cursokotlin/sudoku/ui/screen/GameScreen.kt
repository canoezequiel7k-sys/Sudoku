package com.cursokotlin.sudoku.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cursokotlin.sudoku.domain.generateDummyBoard
import com.cursokotlin.sudoku.domain.validateBoard
import com.cursokotlin.sudoku.ui.composable.SudokuBoard
import com.cursokotlin.sudoku.ui.composable.SudokuKeypad
import kotlinx.coroutines.delay
import kotlin.math.ceil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var board by remember { mutableStateOf(generateDummyBoard()) }
    var selectedCell by remember {mutableStateOf<Pair<Int, Int>?>(null) }
    var seconds by remember { mutableStateOf(0)}

    val context = LocalContext.current //Obtenemos el contexto.
//    //Llamamos a la funcion del tablero temporal
//    val board = generateDummyBoard()
    val minutes = seconds / 60
    val secs = seconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, secs)

    LaunchedEffect(Unit) {
        while (true){
            delay(1000L) //Espera un segundo (1000 milisegundos)
            seconds++ //Suma 1 al contador
        }
    }

    MaterialTheme{
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Dificultad")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Configuracion"
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
                //Temporizador
                Text(
                    text = timeFormatted,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                SudokuBoard(
                    board = board,
                    onCellClick = { row, col ->
                        selectedCell = Pair(row, col) //Guardamos cual fue tocado
                        //Obtenemos el numero de la celda que se acaba de tocar (Puede ser null si esta vacia)
                        val clickedNumber = board[row][col].number

                        //Recorremos el tablero para actualizar isSelected de cada celda
                        board = board.mapIndexed { r, rowList ->
                            rowList.mapIndexed { c, cell ->
                                cell.copy(
                                    isSelected = (r == row && c == col),
                                    //Si la celda tiene un numero y coincide con el numero tocado, se marca
                                    isSameNumber = (clickedNumber != null && cell.number == clickedNumber)
                                )
                            }
                        }
                        Toast.makeText(context, "Celda: $row, $col", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                SudokuKeypad(
                    onNumberClick = {number ->
                        //Si selectedCell no es nula, extraemos su 'r' (fila) y su 'c' (columna)
                        selectedCell?.let { (r, c) ->
                            board = validateBoard(
                                board.mapIndexed { rowIndex, rowList ->
                                    rowList.mapIndexed { colIndex, cell ->
                                        if (rowIndex == r && colIndex == c){
                                            cell.copy(number = number)
                                        }else{
                                            cell
                                        }
                                    }
                                }
                            )
                        }
                    },
                    onEreseClick = {
                        //Si hay una celda seleccionada, la borramos
                        selectedCell?.let { (r, c) ->
                            board = validateBoard(
                                board.mapIndexed { rowIndex, rowList ->
                                    rowList.mapIndexed { colIndex, cell ->
                                        //Solo borramos si coincide con la celda y no es un numero fijo de fabrica(isGiven)
                                        if (rowIndex == r && colIndex == c && !cell.isGiven){
                                            cell.copy(number = null)
                                        }else{
                                            cell
                                        }
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        onBack = {}
    )
}