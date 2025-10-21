package com.example.app_finanzas.Activities.DashboardActivity.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_finanzas.Activities.DashboardActivity.screens.MainScreen
import com.example.app_finanzas.Activities.DashboardActivity.screens.SplashScreen
import com.example.app_finanzas.Activities.ReportActivity.screens.ReportScreen
import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.Domain.ExpenseDomain

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = Modifier.padding(paddingValues)
    ) {
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