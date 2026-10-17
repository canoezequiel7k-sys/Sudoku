package com.cursokotlin.sudoku.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cursokotlin.sudoku.R
import com.cursokotlin.sudoku.ui.composable.SudokuButton

@Preview
@Composable
fun MainScreen(

) {
    MaterialTheme{
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->

            Box(
                modifier = Modifier.fillMaxSize(),
            ){
                //Imagen de fondo
                Image(
                    painter = painterResource(R.drawable.ic_fondov3),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.weight(0.5f))
                    //Logo
                    Image(
                        painter = painterResource(R.drawable.ic_logov3),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //Botones
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        //Boton de iniciar partida
                        SudokuButton(
                            buttonText = "Nueva Partida",
                            onClick = {}
                        )
                        //Estadisticas de jugadores
                        SudokuButton(
                            buttonText = "Estadisticas",
                            onClick = {}
                        )
                        //Configuraciones
                        SudokuButton(
                            buttonText = "Configuraciones",
                            onClick = {}
                        )
                        //Ayuda
                        SudokuButton(
                            buttonText = "Como jugar",
                            onClick = {}
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}