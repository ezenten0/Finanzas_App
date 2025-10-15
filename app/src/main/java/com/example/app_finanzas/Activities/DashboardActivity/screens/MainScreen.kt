package com.example.app_finanzas.Activities.DashboardActivity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.Activities.DashboardActivity.components.CardSection
import com.example.app_finanzas.Domain.ExpenseDomain
import com.example.app_finanzas.Activities.DashboardActivity.components.HeaderSection

@Composable
@Preview(showBackground = true)
fun MainScreenPreview(){
    val expenses = listOf(
        ExpenseDomain("Restaurant",6000,"img1","17 jun 2025 19:15"),
        ExpenseDomain("McDonald's",8990,"img2","16 jun 2025 13:00"),
        ExpenseDomain("Cinemax",6900,"img3","16 jun 2025 15:30"),
        ExpenseDomain("Restaurant",6000,"img1","15 jun 2025 18:59")
    )
        MainScreen( expenses = expenses )
}
@Composable
fun MainScreen(
    onCardClick:()->Unit={},
    expenses:List<ExpenseDomain>
){
    Box (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){

            item { HeaderSection() }
            item { CardSection (onCardClick)}
        }
    }
}