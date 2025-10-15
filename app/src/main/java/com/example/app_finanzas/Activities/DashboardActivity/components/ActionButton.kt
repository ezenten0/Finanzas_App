package com.example.app_finanzas.Activities.DashboardActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.R

@Composable
fun ActionButtonRow(){
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(R.drawable.btn_1,"Depositos")
    }
}

@Composable
fun ActionButton(x0: Int, x1: String) {
    Column {
        modifier = Modifier
            .weight(1f)
            .height(78.dp)
            .clip()
    }
}