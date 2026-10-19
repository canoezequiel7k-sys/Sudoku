package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cursokotlin.sudoku.domain.SudokuCell
import com.cursokotlin.sudoku.domain.extractBlock

//Tablero 9×9 usando bloques
@Composable
fun SudokuBoard(
    board: List<List<SudokuCell>>, // Recibe la matriz gigante completa de 9x9 celdas
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) //Obliga a que todo el tablero sea un cuadrado perfecto
            .border(4.dp, Color.Black) //Borde exterior grueso del tablero
    ){ //Organiza los bloques verticalmente (3 filas de bloques)
        repeat(3){ blockRow ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){ // Organiza los bloques horizontalmente (3 columnas de bloques)
                repeat(3) { blockCol ->
                    SudokuBlock(
                        // Llama a una función auxiliar que recorta
                        // la sección 3x3 correspondiente del tablero gigante
                        cells = extractBlock(board, blockRow, blockCol),
                        onCellClick = onCellClick, // Pasa el click hacia arriba
                        modifier = Modifier
                            .weight(1f) //Reparte el ancho entre los 3 bloques principales
                            .fillMaxHeight()
                            .border(2.dp, Color.Black) //Borde un poco mnas grueso entre bloques 3x3
                    )
                }
            }
        }
    }
}