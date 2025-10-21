package com.example.app_finanzas.Activities.ReportActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_finanzas.R

@Composable
fun SummaryColums(modifier: Modifier = Modifier){
    Row(modifier = modifier.height(IntrinsicSize.Min),verticalAlignment = Alignment.CenterVertically
    ) {
        SummaryColumn(
            title = "Total saldo",
            value = "$500.000",
            percent = "+25%",
            percentColor = Color.Black,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider()

        SummaryColumn(
            title = "Ingresos",
            value = "$500.000",
            percent = "+50%",
            percentColor = Color.Black,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider()
        SummaryColumn(
            title = "Ahorros",
            value = "5.000.000",
            percent = "-10%",
            percentColor = Color.Red,
            modifier = Modifier.weight(1f)
        )


    }
}

@Preview
@Composable
fun SummaryColumsPreview() {
    SummaryColums()
}

@Composable
fun SummaryColumn(
    title: String,
    value: String,
    percent: String,
    percentColor: Color,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
        Text(title,color= colorResource(R.color.blue))
        Text(
            value,color = Color.Black,
            fontWeight =  FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            percent,
            color = percentColor)
    }
}