package com.cursokotlin.sudoku.domain

/**
 Como cada bloque tiene una altura exacta de 3 celdas, para saber en qué fila exacta del tablero grande comienza un bloque, multiplicamos su índice de bloque por 3:

- Si blockRow = 0 (bloque superior): 0 * 3 = 0. Empezamos a leer desde la fila 0 del tablero.

- Si blockRow = 1 (bloque central): 1 * 3 = 3. Empezamos a leer desde la fila 3 del tablero.

- Si blockRow = 2 (bloque inferior): 2 * 3 = 6. Empezamos a leer desde la fila 6 del tablero.
*/

//saber exactamente dónde empieza el bloque de 3x3 que queremos recortar dentro de la matriz gigante de 9x9.
fun extractBlock(
    board: List<List<SudokuCell>>,
    blockRow: Int,
    blockCol: Int,
): List<List<SudokuCell>>{
    //Calcular el punto de partida del bloque
    val startRow = blockRow * 3
    val startCol = blockCol * 3

    //Lista mutable para guardar las filas del bloque
    val blockCells = mutableListOf<List<SudokuCell>>()

    //Bucle externo que recorre las 3 filas del bloque (índices 0, 1 y 2)
    for(r in 0 until 3){
        //Crea una lista temporal vacia para almacenar las 3 celdas de la fila actual (r)
        val rowCell = mutableListOf<SudokuCell>()

        //Bucle interno que recorre las 3 columnas del bloque (índices 0, 1 y 2)
        for (c in 0 until 3){
            //Extrae la celda exacta del tablero gigante sumando la posicion inicial (startRow/startCol) mas el avance del bucle (r/c) y la mete en la fila.
            rowCell.add(board[startRow + r][startCol + c])
        }
        //Una vez terminada las 3 columnas, añade la fila completa a la lista del bloque
        blockCells.add(rowCell)
    }
    //Devuelve el bloque 3x3 ya construido y listo para ser pintado en la UI
    return blockCells
}