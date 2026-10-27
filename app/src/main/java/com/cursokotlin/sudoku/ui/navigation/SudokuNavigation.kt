package com.cursokotlin.sudoku.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cursokotlin.sudoku.ui.screen.DifficultyScreen
import com.cursokotlin.sudoku.ui.screen.GameScreen
import com.cursokotlin.sudoku.ui.screen.MainScreen

@Composable
fun SudokuNavigation() {
    //Creamos el controlador que manejara las rutas
    val navController = rememberNavController()

    //El mapa de navegacion (NavHost) que define las pantallas y su orden
    NavHost(
        navController = navController,
        startDestination = "main_screen" //La pantalla con la que arranca la app
    ){
        //Pantalla 1: Menu principal
        composable("main_screen"){
            MainScreen(
                onNavigateToDifficulty = {
                    //Navegamos a la pantalla de seleccion de dificultad
                    navController.navigate("difficulty_screen")
                }
            )
        }

        //Pantalla 2: Seleccion de dificultad
        composable("difficulty_screen"){
            DifficultyScreen(
                onBack = {
                    //Volvemos al menu principal
                    navController.popBackStack()
                },
                onDifficultySelected = { difficulty ->
                    //Navegamos al juego pasando la dificultad elegida en la ruta
                    navController.navigate("game_screen/$difficulty")
                }
            )
        }

        //Pantalla 3: Pantalla de juego tablero 9x9
        composable("game_screen/{difficulty}"){ backStackEntry ->
            //Extraemos la dificultad de la ruta (Si es nula, por defecto es Normal)
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Normal"

            GameScreen(
                difficulty = difficulty,
                onBack = {
                    //Boton para volver atras en la topAppBar
                    navController.popBackStack()
                }
            )
        }
    }
}
