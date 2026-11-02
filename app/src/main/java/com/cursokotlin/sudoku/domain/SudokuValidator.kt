package com.cursokotlin.sudoku.domain


fun validateBoard(
    board: List<List<SudokuCell>>,
    solution: List<List<Int>>? = null
): List<List<SudokuCell>> {
    //Aplicar la logica para verificar filas, columnas y bloques
    return board.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, cell ->
            val number = cell.number

            //Si la celda estra vacia, no hay error.
            if (number == null){
                return@mapIndexed cell.copy(hasError = false)
            }
            //Esta repetido en la fila r? (excluyendo la columna c)
            val rowConflict = board[r].any { it.col != c && it.number == number}

            //Esta repetido en la columna c? (excluyendo la fila r)
            val colConflict = board.any{ it[c].row != r && it[c].number == number}

            //Esta repetido en su bloque 3x3?
            val blockRow = r / 3
            val blockCol = c / 3
            val blockCells = extractBlock(board, blockRow, blockCol)
            val blockConflict = blockCells.flatten().any{
                (it.row != r || it.col != c) && it.number == number
            }

            //Conflicto si el numero no coincide con la solucion
            val isWrongSolution = solution != null && solution[r][c] != number

            //Si hay conflicto en cualquiera de los tres, hassError = true
            val hasError = rowConflict || colConflict || blockConflict || isWrongSolution
            cell.copy(hasError = hasError)
        }
    }
}