package com.example.app_finanzas.Activities.DashboardActivity.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_finanzas.Activities.DashboardActivity.components.BottomNavigationBar
import com.example.app_finanzas.Domain.ExpenseDomain
import com.example.app_finanzas.R
import com.example.app_finanzas.ViewModel.UsuarioViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val usuarioState by usuarioViewModel.estado.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItemId = when (currentRoute) {
                    "main" -> R.id.wallet
                    else -> R.id.wallet
                },
                onItemSelected = { routeId ->
                    val route = when (routeId) {
                        R.id.wallet -> "main"
                        R.id.future -> "splash"
                        else -> "main"
                    }
                    bottomNavController.navigate(route) {
                        popUpTo(bottomNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.height(80.dp)
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
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
                    onCardClick = { navController.navigate("splash") } // Navega a splash con el NavController principal
                )
            }
            composable("future") {
                // Placeholder for future screen
                Text("Future Screen")
            }
        }
    }
}