package com.example.app_finanzas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.app_finanzas.home.HomeRoute
import com.example.app_finanzas.ui.theme.App_FinanzasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_FinanzasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeRoute()
                }
            }
        }
    }
}
