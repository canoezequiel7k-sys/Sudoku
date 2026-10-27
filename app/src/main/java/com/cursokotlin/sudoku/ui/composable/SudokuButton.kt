package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cursokotlin.sudoku.ui.theme.BlueDark


/**
 * Botón principal de la app con forma pulida y elevación sutil.
 *
 * Se usa en el menú principal para acciones como iniciar partida, ver
 * estadísticas, configuraciones y abrir la ayuda.
 *
 * @param buttonText texto que se muestra dentro del botón.
 * @param onClick acción ejecutada al tocar el botón.
 *
 * La elevación separa visualmente el botón del fondo y le da profundidad.
 * Se elimina el alpha(0.9f) previo porque, al combinarse con elevación,
 * oscurecía el color innecesariamente y restaba legibilidad.
 */
@Composable
fun SudokuButton(
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueDark,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(250.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
