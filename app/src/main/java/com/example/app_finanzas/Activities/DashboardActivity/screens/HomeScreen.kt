package com.example.app_finanzas.Activities.DashboardActivity.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_finanzas.Activities.DashboardActivity.components.BottomNavigationBar
import com.example.app_finanzas.Activities.navigation.AppNavigation
import com.example.app_finanzas.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItemId = when (currentRoute) {
                    "main" -> R.id.wallet
                    // Puedes agregar más casos para otras pantallas
                    else -> 0 // Ninguno seleccionado por defecto
                },
                onItemSelected = { routeId ->
                     val route = when (routeId) {
                        R.id.wallet -> "main"
                        R.id.future ->  "splash"
                        else -> "main"
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.height(80.dp)
            )
        }
    ) { paddingValues ->
        AppNavigation(navController = navController, paddingValues = paddingValues)
    }
}