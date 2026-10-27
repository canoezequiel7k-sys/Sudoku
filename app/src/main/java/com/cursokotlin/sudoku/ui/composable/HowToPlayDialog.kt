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
        text = {
            buildAnnotatedString {
                append("Regla 1: cada fila del 1 al 9 no puede repetir números.\n\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Filas")
                }
                append("\n\n")

                append("Regla 2: cada columna del 1 al 9 no puede repetir números.\n\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Columnas")
                }
                append("\n\n")

                append("Regla 3: cada bloque de 3×3 celdas no puede repetir números.\n\n")
                withStyle(SpanStyle(color = BlueDark, fontWeight = FontWeight.Bold)) {
                    append("Bloques 3×3")
                }
            }
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
                Text(text = "Entendido")
            }
        },
        containerColor = Color.White,
        titleContentColor = BlueDark,
        textContentColor = Color.Black
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
