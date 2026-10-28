package com.cursokotlin.sudoku.domain

/**
 * Resolvedor de Sudoku puro por backtracking.
 *
 * Trabaja siempre sobre una copia del grid recibido para no modificar el original.
 * Un grid con contradicciones iniciales (repetidos en fila, columna o bloque 3x3)
 * no tiene soluciones válidas.
 *
 * countSolutions cuenta hasta un límite (por defecto 2) para detectar si un puzzle
 * tiene solución única (1) o múltiples (>= 2). El límite evita explorar todo el
 * espacio de búsqueda cuando solo importa saber si hay más de una solución.
 */
class SudokuSolver {

    private companion object {
        /** Tamaño fijo del tablero (9x9). */
        const val SIZE = 9
        /** Valor que representa una celda vacía (0). */
        const val EMPTY = 0
    }

    /**
     * Cuenta las soluciones válidas de un grid de Sudoku, deteniéndose al
     * alcanzar [limit].
     *
     * @param grid matriz 9x9 donde 0 representa una celda vacía.
     * @param limit número máximo de soluciones a contar (por defecto 2).
     * @return cantidad de soluciones encontradas, nunca mayor que [limit].
     */
    fun countSolutions(grid: Array<IntArray>, limit: Int = 2): Int {
        val copy = deepCopy(grid)
        if (!isValidInitial(copy)) return 0
        val counter = IntArray(1)
        backtrackCount(copy, limit, counter)
        return counter[0]
    }

    /**
     * Resuelve un grid de Sudoku y devuelve una copia con la solución.
     *
     * @param grid matriz 9x9 donde 0 representa una celda vacía.
     * @return una copia del grid resuelta, o [null] si no tiene solución.
     */
    fun solve(grid: Array<IntArray>): Array<IntArray>? {
        val copy = deepCopy(grid)
        if (!isValidInitial(copy)) return null
        return if (backtrackSolve(copy)) copy else null
    }

    // ------------------------------------------------------------------
    // Backtracking: cuenta soluciones hasta el límite
    // ------------------------------------------------------------------
    private fun backtrackCount(grid: Array<IntArray>, limit: Int, counter: IntArray) {
        if (counter[0] >= limit) return

        val empty = findEmpty(grid)
        if (empty == null) {
            counter[0]++
            return
        }

        val (row, col) = empty
        for (num in 1..9) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num
                backtrackCount(grid, limit, counter)
                if (counter[0] >= limit) return
                grid[row][col] = EMPTY
            }
        }
    }

    // ------------------------------------------------------------------
    // Backtracking: encuentra una solución
    // ------------------------------------------------------------------
    private fun backtrackSolve(grid: Array<IntArray>): Boolean {
        val empty = findEmpty(grid)
        if (empty == null) return true

        val (row, col) = empty
        for (num in 1..9) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num
                if (backtrackSolve(grid)) return true
                grid[row][col] = EMPTY
            }
        }
        return false
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------
    private fun findEmpty(grid: Array<IntArray>): Pair<Int, Int>? {
        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                if (grid[r][c] == EMPTY) return Pair(r, c)
            }
        }
        return null
    }

    private fun isValidPlacement(grid: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        // Fila
        for (c in 0 until SIZE) if (grid[row][c] == num) return false
        // Columna
        for (r in 0 until SIZE) if (grid[r][col] == num) return false
        // Bloque 3x3
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
     * Verifica que el grid inicial no tenga contradicciones (números repetidos
     * en fila, columna o bloque entre celdas ya llenas).
     */
    private fun isValidInitial(grid: Array<IntArray>): Boolean {
        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                val num = grid[r][c]
                if (num != 0) {
                    // Fila
                    for (c2 in 0 until SIZE) {
                        if (c2 != c && grid[r][c2] == num) return false
                    }
                    // Columna
                    for (r2 in 0 until SIZE) {
                        if (r2 != r && grid[r2][c] == num) return false
                    }
                    // Bloque 3x3
                    val startRow = (r / 3) * 3
                    val startCol = (c / 3) * 3
                    for (r2 in startRow until startRow + 3) {
                        for (c2 in startCol until startCol + 3) {
                            if ((r2 != r || c2 != c) && grid[r2][c2] == num) return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun deepCopy(grid: Array<IntArray>): Array<IntArray> {
        // Copia manual de cada IntArray para evitar it.copy() que no existe en IntArray.
        // Se crea un nuevo IntArray con el mismo tamaño y contenido.
        val result = Array(grid.size) { IntArray(grid[0].size) }
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                result[r][c] = grid[r][c]
            }
        }
        return result
    }
}