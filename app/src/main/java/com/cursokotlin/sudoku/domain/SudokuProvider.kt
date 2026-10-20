package com.cursokotlin.sudoku.domain


/**
 * Genera un juego de Sudoku válido a partir de una solución matemática perfecta,
 * ocultando más o menos números según la dificultad elegida.
 */
fun generateSudokuGame(difficulty: String): SudokuGameData {

    //Una solución 100% matemática, perfecta y libre de errores
    val perfectSolution = listOf(
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

    // Decidimos cuántos huecos hacer según la dificultad (ocultar más o menos)
    // Usamos las coordenadas (fila y columna) que queremos dejar vacías (null)
    val emptyIndices = when (difficulty) {
        "Fácil", "Facil" -> setOf(
            Pair(0, 2), Pair(0, 3), Pair(1, 1), Pair(2, 0), Pair(3, 3)
        ) //Pocos huecos (muy fácil)
        "Difícil", "Dificil" -> setOf(
            Pair(0, 2), Pair(0, 3), Pair(0, 5), Pair(0, 6),
            Pair(1, 1), Pair(1, 2), Pair(1, 6), Pair(1, 7),
            Pair(2, 0), Pair(2, 3), Pair(2, 4), Pair(2, 5),
            Pair(3, 1), Pair(3, 2), Pair(3, 4), Pair(3, 6),
            Pair(4, 0), Pair(4, 2), Pair(4, 4), Pair(4, 6),
            Pair(5, 1), Pair(5, 3), Pair(5, 5), Pair(5, 7),
            Pair(6, 0), Pair(6, 2), Pair(6, 4), Pair(6, 8),
            Pair(7, 1), Pair(7, 2), Pair(7, 6), Pair(7, 7),
            Pair(8, 3), Pair(8, 4), Pair(8, 5), Pair(8, 6)
        ) //Muchos huecos (difícil)
        else -> setOf(
            Pair(0, 2), Pair(0, 3), Pair(0, 5),
            Pair(1, 1), Pair(1, 6), Pair(1, 7),
            Pair(2, 0), Pair(2, 4), Pair(2, 5),
            Pair(3, 2), Pair(3, 6),
            Pair(4, 1), Pair(4, 4), Pair(4, 7),
            Pair(5, 0), Pair(5, 3),
            Pair(6, 2), Pair(6, 5), Pair(6, 8),
            Pair(7, 0), Pair(7, 3), Pair(7, 6),
            Pair(8, 1), Pair(8, 4)
        ) //Normal (equilibrado)
    }

    //Construimos el tablero ocultando los números en las posiciones de emptyIndices
    val boardValues = perfectSolution.mapIndexed { r, row ->
        row.mapIndexed { c, num ->
            if (emptyIndices.contains(Pair(r, c))) null else num
        }
    }

    //Convertimos a SudokuCell
    val cellBoard = boardValues.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, value ->
            SudokuCell(
                row = r,
                col = c,
                number = value,
                isGiven = value != null
            )
        }
    }

    return SudokuGameData(board = cellBoard, solution = perfectSolution)
}