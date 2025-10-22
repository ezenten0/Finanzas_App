package com.example.app_finanzas.Activities.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_finanzas.Activities.DashboardActivity.screens.HomeScreen
import com.example.app_finanzas.Activities.DashboardActivity.screens.SplashScreen
import com.example.app_finanzas.Activities.ReportActivity.screens.ReportScreen
import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.ViewModel.UsuarioViewModel
import com.example.app_finanzas.Activities.IntroActivity.screens.IntroScreen
import com.example.app_finanzas.ui.screens.RegistroScreen
import com.example.app_finanzas.ui.screens.ResumenScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "intro"
    ) {
        composable("intro") {
            IntroScreen(onStartClick = { navController.navigate("registro") })
        }
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen") {
            ResumenScreen(navController, usuarioViewModel)
        }
        composable("home") {
            HomeScreen(navController = navController, usuarioViewModel = usuarioViewModel)
        }
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("report") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("report") {
            val budgets = listOf(
                BudgetDomain(title = "Comida", price = 100.0, percent = 20.0),
                BudgetDomain(title = "Transporte", price = 200.0, percent = 30.0),
            )
            ReportScreen(
                budgets = budgets,
                onBack = { navController.popBackStack() }
            )
        }
    }
}