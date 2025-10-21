package com.example.app_finanzas.Activities.DashboardActivity.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.R

@Composable
fun BottomNavigationBar(
    selectedItemId: Int,
    onItemSelected:(Int)->Unit,
    modifier: Modifier
){
    NavigationBar (containerColor = colorResource(R.color.lightBlue),
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
        ) {
        NavigationBarItem(
            selected = selectedItemId == R.id.wallet,
            onClick = { onItemSelected(R.id.wallet) },
            icon = {
                Icon(painter = painterResource(R.drawable.wallet), contentDescription = null)
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selectedItemId == R.id.future,
            onClick = { onItemSelected(R.id.future) },
            icon = {
                Icon(painter = painterResource(R.drawable.futures), contentDescription = null)
            },
            label = { Text("Reporte") }
        )
        NavigationBarItem(
            selected = selectedItemId == R.id.trade,
            onClick = { onItemSelected(R.id.trade) },
            icon = {
                Icon(painter = painterResource(R.drawable.trade), contentDescription = null)
            },
            label = { Text("Trade") }
        )
        NavigationBarItem(
            selected = selectedItemId == R.id.qrpay,
            onClick = { onItemSelected(R.id.qrpay) },
            icon = {
                Icon(painter = painterResource(R.drawable.qrpay), contentDescription = null)
            },
            label = { Text("Pago QR") }
        )
        NavigationBarItem(
            selected = selectedItemId == R.id.profile,
            onClick = { onItemSelected(R.id.profile) },
            icon = {
                Icon(painter = painterResource(R.drawable.profile), contentDescription = null)
            },
            label = { Text("Profile") }
        )
    }
}


@Preview
@Composable
fun BottomNavigationBarPreview(){
    BottomNavigationBar(selectedItemId = R.id.wallet, onItemSelected = {}, modifier = Modifier)
}