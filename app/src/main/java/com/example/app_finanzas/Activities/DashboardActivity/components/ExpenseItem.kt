package com.example.app_finanzas.Activities.DashboardActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_finanzas.Domain.ExpenseDomain
import com.example.app_finanzas.R

@Composable
fun ExpenseItem(item: ExpenseDomain) {
    Row(modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()
        .height(70.dp)
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(Color.White)
        .border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(12.dp)
        ),
        verticalAlignment = Alignment.CenterVertically
    ){
        val context = LocalContext.current
        val resId = context.resources.getIdentifier(
            item.pic, "drawable", context.packageName
        )

        Image(
            painter = painterResource(context.resources.getIdentifier(
                item.pic, "drawable", context.packageName
            )
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.lightBlue))
                .padding(12.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ){
            Text(item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color=colorResource(R.color.darkBlue)
            )
            Text(item.time, color = colorResource(R.color.darkBlue))
        }
        Text(
            "$${item.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color=colorResource(R.color.darkBlue),
            modifier = Modifier.padding(end = 16.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseItemPreview() {
    val expense = ExpenseDomain( title = "Spotify", price = 12, pic = "img_1", time = "10:00")
    ExpenseItem(item = expense)
}

