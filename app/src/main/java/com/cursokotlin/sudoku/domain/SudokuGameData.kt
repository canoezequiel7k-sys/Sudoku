package com.cursokotlin.sudoku.domain


//Identificar la matriz que ve el usuario y la matriz resuelta para las pistas.
data class SudokuGameData(
    val board: List<List<SudokuCell>>,
    val solution: List<List<Int>>
)
