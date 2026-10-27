package com.cursokotlin.sudoku.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cursokotlin.sudoku.ui.theme.BlueDark

/**
 * Diálogo emergente que explica las 3 reglas básicas del Sudoku tradicional.
 *
 * Se muestra como una [AlertDialog] de Material 3 con el estilo de la app:
 * fondo blanco, título y botón de confirmación en [BlueDark].
 *
 * @param show controla la visibilidad del diálogo. Cuando es true se dibuja la
 *             ventana emergente sobre el contenido actual.
 * @param onDismissRequest se invoca cuando el usuario cierra el diálogo
 *                         (toca el botón de confirmación o solicita el descarte).
 */
@Composable
fun HowToPlayDialog(
    show: Boolean,
    onDismissRequest: () -> Unit
) {
    if (!show) return

    val titleStyle = MaterialTheme.typography.titleLarge?.copy(
        color = BlueDark,
        fontWeight = FontWeight.Bold
    ) ?: TextStyle(
        color = BlueDark,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    )
    //Ventana emergente que aparece al presionar el button de ayuda
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Cómo jugar",
                style = titleStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        },
        //Textos de las reglas.
        text = {
            val annotatedString = buildAnnotatedString {
                append("\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Rows\n\n")
                }
                append("\tRule 1: Each row from 1 to 9 cannot contain repeated numbers.")
                append("\n\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Colum\n\n")
                }
                append("\tRule 2: Each column from 1 to 9 cannot contain repeated numbers.")
                append("\n\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Block 3×3\n\n")
                }
                append("\t\nRule 3: Each 3×3 block of cells cannot contain repeated numbers.")

            }
            Text(annotatedString)
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueDark,
                    contentColor = Color.White
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(120.dp)
            ) {
                Text(text = "Understood")
            }
        },
        containerColor = Color.White,
        titleContentColor = BlueDark
    )
}

@Preview(showBackground = true)
@Composable
fun HowToPlayDialogPreview() {
    HowToPlayDialog(
        show = true,
        onDismissRequest = {}
    )
}
