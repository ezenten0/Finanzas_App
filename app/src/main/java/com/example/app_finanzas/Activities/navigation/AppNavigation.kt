package com.example.app_finanzas.Activities.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_finanzas.ViewModel.UsuarioViewModel
import com.example.app_finanzas.ui.screens.RegistroScreen
import com.example.app_finanzas.ui.screens.ResumenScreen


@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    //ViewModel 1 vez
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen") {
            ResumenScreen(usuarioViewModel)
        }
    }
}