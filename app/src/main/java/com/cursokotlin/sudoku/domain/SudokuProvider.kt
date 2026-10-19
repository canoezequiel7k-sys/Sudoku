package com.cursokotlin.sudoku.domain


/**
 * Genera un tablero de prueba (dummy) de 9x9 celdas.
 * Útil para probar la interfaz visual antes de tener la lógica del juego real.
 */
//Queremos que devuelva una matriz 9x9
fun generateSudokuGame(difficulty: String): SudokuGameData {
    // Un puzzle de Sudoku de prueba válido (los null son casillas vacías)
    // NIVEL FÁCIL
    val easySolution = listOf(
        listOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
        listOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        listOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
        listOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        listOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        listOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        listOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        listOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        listOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )
    val easyBoard = easySolution.mapIndexed { _, row ->
        row.mapIndexed { colIdx, num -> if (colIdx % 2 == 0) num else null }
    }

    // NIVEL NORMAL
    val normalSolution = listOf(
        listOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
        listOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        listOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
        listOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        listOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        listOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        listOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        listOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        listOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )
    val normalBoard = listOf(
        listOf(5, 3, null, null, 7, null, null, null, null),
        listOf(6, null, null, 1, 9, 5, null, null, null),
        listOf(null, 9, 8, null, null, null, null, 6, null),
        listOf(8, null, null, null, 6, null, null, null, 3),
        listOf(4, null, null, 8, null, 3, null, null, 1),
        listOf(7, null, null, null, 2, null, null, null, 6),
        listOf(null, 6, null, null, null, null, 2, 8, null),
        listOf(null, null, null, 4, 1, 9, null, null, 5),
        listOf(null, null, null, null, 8, null, null, 7, 9)
    )

    // NIVEL DIFÍCIL
    val hardSolution = listOf(
        listOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
        listOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
        listOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
        listOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
        listOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
        listOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
        listOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
        listOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
        listOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )
    val hardBoard = listOf(
        listOf(null, null, null, null, 7, null, null, null, null),
        listOf(6, null, null, null, null, 5, null, null, null),
        listOf(null, 9, 8, null, null, null, null, null, null),
        listOf(8, null, null, null, null, null, null, null, 3),
        listOf(null, null, null, 8, null, 3, null, null, null),
        listOf(7, null, null, null, null, null, null, null, 6),
        listOf(null, 6, null, null, null, null, 2, 8, null),
        listOf(null, null, null, 4, null, 9, null, null, null),
        listOf(null, null, null, null, 8, null, null, 7, null)
    )

    //Seleccionamos la matriz cruda segun la dificultad que llego por parametro:
    val (rawBoard, solution) = when (difficulty) {
        "Fácil", "Facil" -> Pair(easyBoard, easySolution)
        "Difícil", "Dificil" -> Pair(hardBoard, hardSolution)
        else -> Pair(normalBoard, normalSolution) // Por defecto o "Normal"
    }
    // Convertimos esta matriz de números en nuestra matriz de SudokuCell
    val cellBoard = rawBoard.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, value ->
            SudokuCell(
                row = r,
                col = c,
                number = value,
                isGiven = value != null
            )
        }
    }

    return SudokuGameData(board = cellBoard, solution = solution)
}