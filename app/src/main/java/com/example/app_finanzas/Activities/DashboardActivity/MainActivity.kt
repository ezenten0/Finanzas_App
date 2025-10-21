package com.example.app_finanzas.Activities.DashboardActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_finanzas.Activities.DashboardActivity.screens.HomeScreen
import com.example.app_finanzas.ui.theme.App_FinanzasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_FinanzasTheme {
                HomeScreen()
            }
        }
    }
}