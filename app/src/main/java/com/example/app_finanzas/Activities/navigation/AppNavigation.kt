package com.example.app_finanzas.Activities.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_finanzas.Activities.DashboardActivity.screens.MainScreen
import com.example.app_finanzas.Activities.DashboardActivity.screens.SplashScreen
import com.example.app_finanzas.ViewModel.UsuarioViewModel
import com.example.app_finanzas.Activities.IntroActivity.screens.IntroScreen
import com.example.app_finanzas.Activities.ReportActivity.screens.ReportScreen
import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.Domain.ExpenseDomain
import com.example.app_finanzas.ui.screens.RegistroScreen
import com.example.app_finanzas.ui.screens.ResumenScreen


@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    //ViewModel 1 vez
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
        composable("main") {
            val expenses = listOf(
                ExpenseDomain("Resturant", 6000, "resturant", "17 jun 2025 19:15"),
                ExpenseDomain("McDonald's", 8990, "mcdonald", "16 jun 2025 13:00"),
            )
            MainScreen(
                expenses = expenses,
                onCardClick = { navController.navigate("splash") } // Navega a splash
            )
        }
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("report") {
                    // Reemplaza splash con report para que el usuario no pueda volver atrás
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
