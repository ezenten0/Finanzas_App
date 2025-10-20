package com.example.app_finanzas.Activities.DashboardActivity.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_finanzas.R

@Composable
@Preview
fun ActionButtonRow() {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionButton(R.drawable.btn_1, "Depositos")
        ActionButton(R.drawable.btn_2, "Retiros")
        ActionButton(R.drawable.btn_3, "+ Tarjeta")
        ActionButton(R.drawable.btn_4, "Ver todo")
    }
}

@Composable
fun RowScope.ActionButton(icon: Int, text: String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .height(78.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(R.color.lightBlue))
            .clickable {}
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = text,
            color = colorResource(id = R.color.darkBlue),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}