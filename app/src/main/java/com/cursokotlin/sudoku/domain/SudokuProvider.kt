package com.cursokotlin.sudoku.domain


/**
 * Genera un tablero de prueba (dummy) de 9x9 celdas.
 * Útil para probar la interfaz visual antes de tener la lógica del juego real.
 */
//Queremos que devuelva una matriz 9x9
fun generateDummyBoard(): List<List<SudokuCell>> {
    // Un puzzle de Sudoku de prueba válido (los null son casillas vacías)
    val rawValues = listOf(
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

    // Convertimos esta matriz de números en nuestra matriz de SudokuCell
    return rawValues.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, value ->
            SudokuCell(
                row = r,
                col = c,
                number = value,
                isGiven = value != null // Si tiene número inicial, es fijo de fábrica (no borrable)
            )
        }
    }
}