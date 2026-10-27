package com.cursokotlin.sudoku.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val Blue = Color(0xFF77B5EC)
val BlueDark = Color(0xFF68A3DA)
val GridLine = Color(0xFFA7CDE8)
val OuterBorder = Color(0xFFA8D8F0)
val Circle = Color(0xFFB0D2EF)
val White = Color(0xFFFFFFFF)

/**
 * Tonos de azul para diferenciar los 3 niveles de dificultad, de más claro a más oscuro.
 * Se derivan de los colores existentes de la app para mantener coherencia visual.
 * No se usan verde, naranja ni rojo.
 */
val DifficultyEasy = Circle     // azul claro: nivel fácil
val DifficultyNormal = Blue     // azul medio: nivel normal
val DifficultyHard = BlueDark   // azul oscuro: nivel difícil