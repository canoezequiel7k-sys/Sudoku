package com.cursokotlin.sudoku.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cursokotlin.sudoku.R
import com.cursokotlin.sudoku.ui.theme.DifficultyEasy
import com.cursokotlin.sudoku.ui.theme.DifficultyHard
import com.cursokotlin.sudoku.ui.theme.DifficultyNormal

/**
 * Pantalla de seleccion de dificultad que reemplaza al dialogo de dificultad anterior.
 *
 * Muestra un [Scaffold] con [TopAppBar] (flecha de regreso + titulo) y tres [Card]s,
 * una por nivel de dificultad. Cada tarjeta tiene un tono de azul distinto para diferenciar
 * visualmente los niveles (de mas claro a mas oscuro).
 *
 * Al tocar una tarjeta se invoca [onDifficultySelected] con el valor de navegacion esperado
 * por [SudokuProvider] ("Facil", "Normal" o "Dificil"). El texto visible en la tarjeta
 * proviene de [strings.xml] en ingles; el valor de navegacion no cambia.
 *
 * @param onDifficultySelected se llama cuando el usuario elige un nivel. Recibe el valor
 *                             de navegacion en espanol que [SudokuProvider] reconoce.
 * @param onBack se llama cuando el usuario presiona la flecha de regreso en la barra superior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyScreen(
    onDifficultySelected: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.difficulty_screen_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Text(
                            text = "\u2190",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DifficultyCard(
                title = stringResource(R.string.difficulty_easy),
                description = stringResource(R.string.difficulty_easy_description),
                color = DifficultyEasy,
                navigationValue = "Facil",
                onClick = onDifficultySelected
            )
            Spacer(modifier = Modifier.height(20.dp))
            DifficultyCard(
                title = stringResource(R.string.difficulty_normal),
                description = stringResource(R.string.difficulty_normal_description),
                color = DifficultyNormal,
                navigationValue = "Normal",
                onClick = onDifficultySelected
            )
            Spacer(modifier = Modifier.height(20.dp))
            DifficultyCard(
                title = stringResource(R.string.difficulty_hard),
                description = stringResource(R.string.difficulty_hard_description),
                color = DifficultyHard,
                navigationValue = "Dificil",
                onClick = onDifficultySelected
            )
        }
    }
}

/**
 * Tarjeta individual de un nivel de dificultad.
 *
 * Muestra titulo, descripcion y un indicador visual de color. Al tocarla se invoca
 * [onClick] con el [navigationValue] correspondiente.
 *
 * @param title texto del nivel (de strings.xml).
 * @param description descripcion corta del nivel (de strings.xml).
 * @param color tono de azul que identifica visualmente el nivel.
 * @param navigationValue valor enviado al seleccionar ("Facil", "Normal" o "Dificil").
 * @param onClick callback con el valor de navegacion.
 */
@Composable
private fun DifficultyCard(
    title: String,
    description: String,
    color: Color,
    navigationValue: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(280.dp)
            .clickable(enabled = true) { onClick(navigationValue) },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.12f)
        ),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                val titleStyle = MaterialTheme.typography.titleMedium?.copy(
                    color = color,
                    fontWeight = FontWeight.Bold
                ) ?: TextStyle(
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    style = titleStyle
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "\u25BA",
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DifficultyScreenPreview() {
    DifficultyScreen(
        onDifficultySelected = {},
        onBack = {}
    )
}
