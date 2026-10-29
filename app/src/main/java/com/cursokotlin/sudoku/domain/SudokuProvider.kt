package com.cursokotlin.sudoku.domain

/**
 * Genera un juego de Sudoku válido para una dificultad dada.
 *
 * Delega la generación en [SudokuGenerator], que produce un tablero completo
 * aleatorio y lo vacía verificando que siempre quede exactamente una solución.
 *
 * @param difficulty texto de dificultad ("Fácil"/"Facil", "Medio"/"Normal",
 *                   "Difícil"/"Dificil").
 * @return [SudokuGameData] con el tablero de juego y su solución.
 */
fun generateSudokuGame(difficulty: String): SudokuGameData {
    return SudokuGenerator().generate(difficulty)
}
