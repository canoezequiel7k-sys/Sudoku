package com.cursokotlin.sudoku.domain


/**
 * Genera un tablero de prueba (dummy) de 9x9 celdas.
 * Útil para probar la interfaz visual antes de tener la lógica del juego real.
 */
//Queremos que devuelva una matriz 9x9
fun generateDummyBoard(): List<List<SudokuCell>>
{
    //Creamos la lista exterior de 9 filas(indice 0 a 8)
    return List(9){ row ->
        //Por cada fila, creamos una lista interior de 9 columnas (indice 0 a 8)
        List(9){ col ->
            SudokuCell(
                row = row,
                col = col,
                number = 3, //Rellenamos temporalmente con el 3 para probar la UI
                isGiven = true, //Marcamos como numero fijo de fabrica.
            )
        }
    }
}