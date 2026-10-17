package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.cursokotlin.sudoku.domain.SudokuCell

//Tablero 9×9 usando bloques
@Composable
fun SudokuBoard(
    board: List<List<SudokuCell>>, // Recibe la matriz gigante completa de 9x9 celdas
    onCellClick: (Int, Int) -> Unit
) {
    Column{ //Organiza los bloques verticalmente (3 filas de bloques)
        repeat(3){ blockRow ->
            Row{ // Organiza los bloques horizontalmente (3 columnas de bloques)
                repeat(3) { blockCol ->
                    SudokuBlock(
                        // Llama a una función auxiliar que recorta
                        // la sección 3x3 correspondiente del tablero gigante
                        cells = extractBlock(board, blockRow, blockCol),
                        onCellClick = onCellClick // Pasa el click hacia arriba
                    )
                }
            }
        }
    }
}