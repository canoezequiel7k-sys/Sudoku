package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SubdirectoryArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuKeypad(
    completedNumbers: Set<Int> = emptySet(),
    onNumberClick: (Int) -> Unit,
    onEreseClick: () -> Unit,
    onUndoClick: () -> Unit,
    onHintClick: () -> Unit
) {

    Column(modifier = Modifier
        .fillMaxWidth()
    ){
        //Botones de acciones especificas como borrar, pistas, etc...
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Button Delete
            IconButton(
                onClick = {onEreseClick()},
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        color = Color(0x80FF85A9),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color(0xFFF44336)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar"
                    )
                    Text(text = "Borrar")
                }

            }
            //Button Undo
            IconButton(
                onClick = {onUndoClick()},
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        color = Color(0x80FF9800),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color(0xFFFF9800)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SubdirectoryArrowLeft,
                        contentDescription = "Retroceder"
                    )
                    Text(text = "Deshacer")
                }

            }
            //Button Hint
            IconButton(
                onClick = {onHintClick()},
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        color = Color(0x804CAF50),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color(0xFF369D3A)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pista"
                    )
                    Text(text = "Pista")
                }

            }

        }

        Spacer(modifier = Modifier.height(42.dp))

        //"Teclado numerico"
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (number in 1..9){
                //Comprobamos si se completo
                val isCompleted = completedNumbers.contains(number)
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f)
                        .background(
                            color = Color(0xFFD0E3FF),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .alpha(if (isCompleted) 0.4f else 1f) //Desvanece si ya se completo
                        .clickable(enabled = !isCompleted) {
                            onNumberClick(number) },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = number.toString(),
                        textAlign = TextAlign.Center,
                        color = if (isCompleted) Color.DarkGray else Color(0xFF0056B3),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SudokuKeyPadPreview() {
    SudokuKeypad(
        onNumberClick = {},
        onEreseClick = {},
        onUndoClick = {},
        onHintClick = {},
    )
}