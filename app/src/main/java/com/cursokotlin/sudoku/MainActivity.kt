package com.cursokotlin.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cursokotlin.sudoku.ui.navigation.SudokuNavigation
import com.cursokotlin.sudoku.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //El punto de entrada de la app
            SudokuNavigation()
        }
    }
}