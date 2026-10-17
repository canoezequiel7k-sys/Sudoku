package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.cursokotlin.sudoku.domain.SudokuCell

//Bloque 3×3 básico
@Composable
fun SudokuBlock(
    cells: List<List<SudokuCell>>, //Recibe una sub-matriz de 3x3 celdas
    onCellClick: (rowInBoard: Int, colInBoard: Int) -> Unit
) {
    // Organiza las 3 filas verticalmente
    Column{
        repeat(3) { r -> // Bucle de 0 a 2 para las filas del bloque
            //Organiza las 3 celdas horizontalmente en cada fila
            Row{
                repeat(3) { c -> // Bucle de 0 a 2 para las columnas del bloque
                    val cell = cells[r][c] // Obtiene la celda específica de la posición [r][c]
                    SudokuCellItem(
                        cell = cell,
                        // Cuando esta celda específica es presionada,
                        // "sube" el aviso hacia arriba usando cell.row y cell.col
                        onClick = {onCellClick(cell.row, cell.col)}
                    )
                }
            }
        }
    }
}