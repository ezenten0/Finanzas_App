package com.example.app_finanzas.Activities.ReportActivity.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import  androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.Activities.ReportActivity.components.CenterStatsCard
import com.example.app_finanzas.Activities.ReportActivity.components.GradientHeader
import com.example.app_finanzas.Activities.ReportActivity.components.SummaryColums
import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.app_finanzas.Activities.DashboardActivity.components.BottomNavigationBar
import com.example.app_finanzas.Activities.ReportActivity.components.BudgetItem


@Composable
fun ReportScreen(
    budgets:List<BudgetDomain>,
    onBack:()->Unit
){
    ConstraintLayout (modifier = Modifier.fillMaxSize()){
        val (scrollRef,bottomBarRef)=createRefs()

            ReportContent(
                budgets = budgets,
                modifier = Modifier.constrainAs(scrollRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBarRef.top)
                },
                onBack =  onBack
            )
        BottomNavigationBar(
            modifier = Modifier
                .height(80.dp)
                .constrainAs(bottomBarRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onItemSelected = {itemId->
                if(itemId==R.id.wallet) {
                }

            }
        )

    }
}

@Composable
fun ReportContent(
    budgets: List<BudgetDomain>,
    modifier: Modifier = Modifier,
    onBack:()-> Unit
) {
    LazyColumn(
        modifier = modifier.background(Color.White),

        ) {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                val (header, card) = createRefs()
                GradientHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .constrainAs(header) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    onBack = onBack

                )
                CenterStatsCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp)
                        .constrainAs(card) {
                            top.linkTo(header.bottom)
                            bottom.linkTo(header.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

            }
        }

        item {
            SummaryColums(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )

        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    "Mi presupuesto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.black)
                )
                Text("editar", color = colorResource(R.color.black))

            }
        }
        itemsIndexed(budgets) { index, item ->
            BudgetItem(budget = item, index = index)
        }
    }
}
@Preview
@Composable
fun ReportScreenPreview(){
    val budgets = listOf(
        BudgetDomain(title = "Comida", price = 100.0, percent = 20.0),
        BudgetDomain(title = "Transporte", price = 200.0, percent = 30.0),
        BudgetDomain(title = "Salud", price = 300.0, percent = 40.0),

    )
    ReportScreen(budgets = budgets, onBack = {})
}