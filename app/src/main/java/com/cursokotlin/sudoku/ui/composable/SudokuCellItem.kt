package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cursokotlin.sudoku.domain.SudokuCell

@Composable
fun SudokuCellItem(
    cell: SudokuCell,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            //Obliga a que la celda sea un cuadrado perfecto (ancho == alto)
            .border(
                //Dibuja el borde exterior de la celda
                width = if (cell.hasError) 2.dp else 1.dp, //Borde fine entre celdas
                color = if (cell.hasError) Color.Red else Color.Gray
            )
            .clickable { onClick() }, //Hace que la celda responda al tacto del usuario
        //Centra el texto dentro del cuadrado
        contentAlignment = Alignment.Center
    ) {
        Text(
            //Muestra el número si existe (convirtiéndolo a String), o vacío si es null
            text = cell.number?.toString() ?: "",
            //Si es un número dado por el juego, se pone en Negrita (Bold)
            fontWeight = if (cell.isGiven) FontWeight.Bold else FontWeight.Normal
        )
    }
}