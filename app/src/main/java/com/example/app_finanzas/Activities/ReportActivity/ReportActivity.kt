package com.example.app_finanzas.Activities.ReportActivity

import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_finanzas.Activities.ReportActivity.screens.ReportScreen
import com.example.app_finanzas.R
import com.example.app_finanzas.ViewModel.MainViewModel



class ReportActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ReportScreen(
                budgets = mainViewModel.loadBudget(),
                onBack = {finish()}
            )
        }

    }
}