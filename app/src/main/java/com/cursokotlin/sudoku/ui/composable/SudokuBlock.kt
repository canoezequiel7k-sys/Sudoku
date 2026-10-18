package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cursokotlin.sudoku.domain.SudokuCell

//Bloque 3×3 básico
@Composable
fun SudokuBlock(
    cells: List<List<SudokuCell>>, //Recibe una sub-matriz de 3x3 celdas
    onCellClick: (rowInBoard: Int, colInBoard: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Organiza las 3 filas verticalmente
    Column(modifier = modifier){
        repeat(3) { r -> // Bucle de 0 a 2 para las filas del bloque
            //Organiza las 3 celdas horizontalmente en cada fila
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                repeat(3) { c -> // Bucle de 0 a 2 para las columnas del bloque
                    val cell = cells[r][c] // Obtiene la celda específica de la posición [r][c]
                    SudokuCellItem(
                        cell = cell,
                        // Cuando esta celda específica es presionada,
                        // "sube" el aviso hacia arriba usando cell.row y cell.col
                        onClick = {onCellClick(cell.row, cell.col)},
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}