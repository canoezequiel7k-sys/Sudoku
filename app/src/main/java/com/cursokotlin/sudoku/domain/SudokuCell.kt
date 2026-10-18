package com.cursokotlin.sudoku.domain

data class SudokuCell(
    //Saber donde esta
    val row: Int,
    val col: Int,
    //Saber que numero tiene
    val number: Int? = null,
    //Saber si el usuario la tocó
    val isSelected: Boolean = false,
    //Saber si vino de fábrica
    val isGiven: Boolean = false, //Pista
    //Saber si hay un conflicto numérico
    val hasError: Boolean = false, //Error
    val isSameNumber: Boolean = false
)
