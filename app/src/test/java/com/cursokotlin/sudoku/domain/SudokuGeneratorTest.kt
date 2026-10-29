package com.cursokotlin.sudoku.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias para [SudokuGenerator].
 *
 * Verifica que los tableros generados:
 * a) tengan exactamente 1 solución (countSolutions == 1),
 * b) coincidan con la solución devuelta en SudokuGameData.solution,
 * c) sean diferentes entre llamadas sucesivas (aleatoriedad),
 * d) tengan la cantidad de celdas dadas (isGiven == true) dentro del rango esperado.
 */
class SudokuGeneratorTest {

    private val generator = SudokuGenerator()
    private val solver = SudokuSolver()

    /**
     * Verifica que los tableros generados para cada dificultad tengan
     * exactamente 1 solución matemática válida.
     */
    @Test
    fun generatedBoardsHaveExactlyOneSolution() {
        val difficulties = listOf("Fácil", "Facil", "Medio", "Normal", "Difícil", "Dificil")

        for (difficulty in difficulties) {
            val game = generator.generate(difficulty)

            // Convierte List<List<SudokuCell>> a Array<IntArray> (0 = vacío)
            val grid = game.board.map { row ->
                row.map { it.number ?: 0 }.toIntArray()
            }.toTypedArray()

            val count = solver.countSolutions(grid, limit = 2)
            assertEquals("El tablero de dificultad '$difficulty' debe tener exactamente 1 solución", 1, count)
        }
    }

    /**
     * Verifica que el tablero generado coincida con la solución devuelta:
     * cada celda dada (isGiven == true) debe tener el mismo número en board y solution.
     */
    @Test
    fun generatedBoardMatchesSolution() {
        val game = generator.generate("Normal")

        for (r in game.board.indices) {
            for (c in game.board[r].indices) {
                val cell = game.board[r][c]
                if (cell.isGiven) {
                    assertEquals(
                        "La celda dada ($r, $c) del tablero debe coincidir con la solución",
                        cell.number,
                        game.solution[r][c]
                    )
                }
            }
        }
    }

    /**
     * Verifica que dos llamadas sucesivas generen tableros con valores o posiciones
     * diferentes (aleatoriedad).
     */
    @Test
    fun consecutiveGenerationsAreDifferent() {
        val game1 = generator.generate("Medio")
        val game2 = generator.generate("Medio")

        val flat1 = game1.board.flatMap { it.map { it.number } }
        val flat2 = game2.board.flatMap { it.map { it.number } }

        assertTrue("Dos partidas generadas no deben ser idénticas", flat1 != flat2)
    }

    /**
     * Verifica que la cantidad de celdas dadas (isGiven == true) esté dentro del
     * rango esperado por dificultad.
     */
    @Test
    fun givensCountIsWithinExpectedRange() {
        val expectedRanges = mapOf(
            "Fácil" to 35..40,
            "Facil" to 35..40,
            "Medio" to 30..34,
            "Normal" to 30..34,
            "Difícil" to 26..29,
            "Dificil" to 26..29
        )

        for ((difficulty, range) in expectedRanges) {
            val game = generator.generate(difficulty)
            val givens = game.board.flatMap { it }.count { it.isGiven }

            assertTrue(
                "La cantidad de pistas dadas ($givens) de '$difficulty' debe estar en el rango $range",
                givens in range
            )
        }
    }
}
