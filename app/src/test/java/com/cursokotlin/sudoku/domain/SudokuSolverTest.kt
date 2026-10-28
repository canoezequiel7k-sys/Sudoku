package com.cursokotlin.sudoku.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertArrayEquals
import org.junit.Test

/**
 * Pruebas unitarias para [SudokuSolver].
 *
 * Verifica que el resolvedor por backtracking:
 * - cuente correctamente las soluciones hasta un límite,
 * - resuelva puzzles válidos,
 * - rechace grids con contradicciones iniciales,
 * - no modifique el grid original recibido.
 */
class SudokuSolverTest {

    private val solver = SudokuSolver()

    /**
     * Verifica que un tablero completo y válido (sin celdas vacías)
     * tenga exactamente 1 solución.
     */
    @Test
    fun completeValidBoardHasOneSolution() {
        // Tablero resuelto del SudokuProvider (sin celdas vacías).
        val grid = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        val count = solver.countSolutions(grid)
        assertEquals(1, count)

        val solved = solver.solve(grid)
        assertNotNull("Un tablero completo válido debe poder resolverse", solved)
        assertArrayEquals(grid[0], solved!![0])
        assertArrayEquals(grid[8], solved!![8])
    }

    /**
     * Verifica que un puzzle del nivel Fácil de SudokuProvider (su solución
     * con los 5 huecos que deja vacíos) tenga 1 solución y que solve la reproduzca.
     */
    @Test
    fun easyPuzzleFromProviderHasOneSolution() {
        // Solución perfecta de SudokuProvider con los 5 huecos del nivel Fácil
        // (0,2), (0,3), (1,1), (2,0), (3,3).
        val grid = arrayOf(
            intArrayOf(5, 3, 0, 0, 7, 8, 9, 1, 2),
            intArrayOf(6, 0, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(0, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 0, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        val expected = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        val count = solver.countSolutions(grid)
        assertEquals(1, count)

        val solved = solver.solve(grid)
        assertNotNull("El puzzle del nivel Fácil debe poder resolverse", solved)
        for (r in 0 until 9) {
            assertArrayEquals(expected[r], solved!![r])
        }
    }

    /**
     * Verifica que un tablero vacío (todos ceros) devuelva al menos 2
     * soluciones cuando el límite es 2, es decir, countSolutions se detiene
     * al alcanzar el límite sin explorar todo el espacio de búsqueda.
     */
    @Test
    fun emptyBoardReturnsLimitSolutions() {
        val grid = Array(9) { intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0) }

        val count = solver.countSolutions(grid, limit = 2)
        assertEquals(2, count)
    }

    /**
     * Verifica que un tablero con contradicción inicial (número repetido en una fila)
     * devuelva 0 soluciones y que solve devuelva null.
     */
    @Test
    fun contradictoryBoardHasNoSolution() {
        // Dos 5 en la misma fila (posición 0,0 y 0,1): contradicción inmediata.
        val grid = arrayOf(
            intArrayOf(5, 5, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        )

        val count = solver.countSolutions(grid)
        assertEquals(0, count)

        val solved = solver.solve(grid)
        assertNull("Un tablero con contradicción inicial no debe tener solución", solved)
    }

    /**
     * Verifica que solve no modifique el grid original recibido.
     */
    @Test
    fun solveDoesNotModifyOriginalGrid() {
        val original = arrayOf(
            intArrayOf(5, 3, 0, 6, 7, 8, 9, 1, 2),
            intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )

        // Copia profunda para comparar después.
        val expected = original.map { intArrayOf(*it) }.toTypedArray()

        solver.solve(original)

        for (r in 0 until 9) {
            assertArrayEquals(expected[r], original[r])
        }
    }
}
