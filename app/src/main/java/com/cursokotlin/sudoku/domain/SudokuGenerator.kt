package com.cursokotlin.sudoku.domain

import kotlin.random.Random

/**
 * Generador de partidas de Sudoku con solución única garantizada.
 *
 * El algoritmo tiene dos etapas:
 * 1. Generar un tablero 9×9 completo, válido y aleatorio.
 * 2. Vaciar celdas aleatoriamente verificando con [SudokuSolver.countSolutions]
 *    que el tablero resultante conserve exactamente una solución matemática.
 *
 * Toda la lógica es Kotlin puro, sin dependencias de UI, Android Framework ni Jetpack Compose.
 */
class SudokuGenerator(private val solver: SudokuSolver = SudokuSolver()) {

    /**
     * Niveles de dificultad con la cantidad objetivo de pistas (celdas dadas)
     * que debe quedar en el tablero final.
     */
    enum class Difficulty(val minGivens: Int, val maxGivens: Int) {
        Easy(35, 40),   // ~41-46 huecos
        Medium(30, 34), // ~47-51 huecos
        Hard(26, 29)    // ~52-55 huecos
    }

    private val random = Random

    /**
     * Genera una partida completa de Sudoku para la [difficulty] dada.
     *
     * Acepta variantes con y sin tilde ("Fácil"/"Facil", "Medio"/"Normal",
     * "Difícil"/"Dificil") para mantener retrocompatibilidad con el valor que
     * envía la navegación actual.
     *
     * @param difficulty texto de dificultad ("Fácil", "Facil", "Medio", "Normal",
     *                   "Difícil", "Dificil", etc.).
     * @return [SudokuGameData] con el tablero de juego y su solución.
     */
    fun generate(difficulty: String): SudokuGameData {
        val level = parseDifficulty(difficulty)
        val completed = generateCompletedBoard()
        val targetGivens = (level.minGivens..level.maxGivens).random()
        val puzzle = removeCellsUntilUnique(completed, targetGivens)

        val board = puzzle.mapIndexed { r, row ->
            row.mapIndexed { c, value ->
                SudokuCell(
                    row = r,
                    col = c,
                    number = if (value == 0) null else value,
                    isGiven = value != 0
                )
            }
        }
        val solution = completed.map { it.toList() }

        return SudokuGameData(board = board, solution = solution)
    }

    private fun parseDifficulty(difficulty: String): Difficulty {
        return when (difficulty.lowercase()) {
            "fácil", "facil", "easy" -> Difficulty.Easy
            "difícil", "dificil", "hard" -> Difficulty.Hard
            else -> Difficulty.Medium // cubre "normal", "medio", "medium" y valores inesperados
        }
    }

    /**
     * Genera un tablero 9×9 completamente resuelto y válido, con números aleatorios.
     *
     * Estrategia:
     * 1. Rellenar los 3 bloques diagonales (0,0), (1,1), (2,2) con permutaciones
     *    aleatorias de 1..9. Estos bloques no comparten filas ni columnas entre sí,
     *    por lo que no generan conflictos Sudoku entre ellos.
     * 2. Completar el resto del tablero con backtracking probando números en orden
     *    aleatorio, lo que garantiza variabilidad entre generaciones.
     */
    private fun generateCompletedBoard(): Array<IntArray> {
        val grid = Array(9) { IntArray(9) }

        // Paso 1: bloques diagonales aleatorios (independientes entre sí)
        for (block in 0 until 3) {
            val start = block * 3
            val numbers = (1..9).toList().shuffled()
            var idx = 0
            for (r in start until start + 3) {
                for (c in start until start + 3) {
                    grid[r][c] = numbers[idx]
                    idx++
                }
            }
        }

        // Paso 2: completar el resto con backtracking aleatorio
        fillRemaining(grid, 0, 0)

        return grid
    }

    /**
     * Backtracking recursivo para completar celdas vacías del [grid].
     * Prueba los números en orden aleatorio para obtener variabilidad.
     *
     * @return true si se completó todo el tablero, false si no hay solución desde este estado.
     */
    private fun fillRemaining(grid: Array<IntArray>, row: Int, col: Int): Boolean {
        var nextRow = row
        var nextCol = col + 1
        if (nextCol == 9) {
            nextRow = row + 1
            nextCol = 0
        }

        if (row == 9) return true // todo lleno

        if (grid[row][col] != 0) {
            return fillRemaining(grid, nextRow, nextCol)
        }

        val numbers = (1..9).toList().shuffled()
        for (num in numbers) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num
                if (fillRemaining(grid, nextRow, nextCol)) return true
                grid[row][col] = 0 // deshacer
            }
        }
        return false
    }

    private fun isValidPlacement(grid: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (c in 0 until 9) if (grid[row][c] == num) return false
        for (r in 0 until 9) if (grid[r][col] == num) return false
        val startRow = (row / 3) * 3
        val startCol = (col / 3) * 3
        for (r in startRow until startRow + 3) {
            for (c in startCol until startCol + 3) {
                if (grid[r][c] == num) return false
            }
        }
        return true
    }

    /**
     * Vacía celdas del [completed] hasta alcanzar aproximadamente [targetGivens]
     * celdas con número, garantizando que el resultado tenga exactamente 1 solución.
     *
     * Para cada celda candidata (en orden aleatorio), se intenta remover su número.
     * Si [SudokuSolver.countSolutions] con limit=2 devuelve más de 1, se restaura
     * el número (la celda no se puede vaciar sin romper la unicidad).
     *
     * Se realizan varias pasadas barajadas para acercarse al objetivo sin entrar
     * en bucles infinitos.
     */
    private fun removeCellsUntilUnique(completed: Array<IntArray>, targetGivens: Int): Array<IntArray> {
        val grid = completed.map { it.copyOf() }.toTypedArray()
        var currentGivens = 81
        val maxPasses = 20

        for (pass in 1..maxPasses) {
            if (currentGivens <= targetGivens) break
            val positions = (0 until 81).toList().shuffled()
            for (pos in positions) {
                if (currentGivens <= targetGivens) break
                val r = pos / 9
                val c = pos % 9
                if (grid[r][c] == 0) continue

                val original = grid[r][c]
                grid[r][c] = 0

                if (solver.countSolutions(grid, limit = 2) == 1) {
                    currentGivens-- // se pudo vaciar: queda una pista menos
                } else {
                    grid[r][c] = original // deshacer: múltiples soluciones, no se puede vaciar
                }
            }
        }

        return grid
    }
}
