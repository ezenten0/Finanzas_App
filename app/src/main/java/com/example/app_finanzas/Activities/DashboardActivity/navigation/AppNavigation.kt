package com.example.app_finanzas.Activities.DashboardActivity.navigation

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_finanzas.Activities.DashboardActivity.screens.MainScreen
import com.example.app_finanzas.Activities.DashboardActivity.screens.SplashScreen
import com.example.app_finanzas.Activities.ReportActivity.screens.ReportScreen
import com.example.app_finanzas.Activities.qrActivity.QrActivity
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
        composable("qrPay") {
            val context = LocalContext.current
            LaunchedEffect(Unit) { // LaunchedEffect ensures this code runs once when the composable enters composition
                val intent = Intent(context, QrActivity::class.java)
                context.startActivity(intent)
                // IMPORTANT: Pop this composable off the back stack immediately
                // as it's just a launcher. Otherwise, you'll have an empty screen
                // when returning from QrActivity.
                navController.popBackStack()
                // Or navigate back to 'main'
                // navController.navigate("main") {
                //    popUpTo("main") { inclusive = true }
                //    launchSingleTop = true
                // }
            }
            // You might want to show a simple loading indicator while the activity launches
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}