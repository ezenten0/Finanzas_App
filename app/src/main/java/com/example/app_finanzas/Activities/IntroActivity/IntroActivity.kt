package com.example.app_finanzas.activities.introactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app_finanzas.Activities.DashboardActivity.MainActivity
import com.example.app_finanzas.Activities.IntroActivity.screens.AuthIntroScreen // <- nuevo

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Mostrar pantalla de autenticación (login/registro).
            AuthIntroScreen(
                onAuthSuccess = {
                    // navegación después de login/registro exitoso
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            )
        }
    }
}