package com.example.app_finanzas.Activities.IntroActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.app_finanzas.activities.introactivity.screens.IntroScreen
import com.example.app_finanzas.Activities.DashboardActivity.MainActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntroScreen (onStartClick= {
                startActivity(Intent(this, MainActivity::class.java))
            })
        }
    }
}