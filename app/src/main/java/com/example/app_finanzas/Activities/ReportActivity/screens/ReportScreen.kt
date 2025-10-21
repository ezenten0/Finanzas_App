package com.example.app_finanzas.Activities.ReportActivity.screens

import androidx.compose.foundation.background
import  androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.Activities.ReportActivity.components.GradientHeader
import com.example.app_finanzas.Domain.BudgetDomain


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
                onBack = {
                    onBack()

                }
            )
    }
}

@Composable
fun ReportContent(
    budgets: List<BudgetDomain>,
    modifier: Modifier = Modifier,
    onBack:()-> Unit
){
    LazyColumn(
        modifier = modifier.background(Color.White),

    ) {
        item {
            ConstraintLayout (modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
            ){
                val (header,card)=createRefs()
                GradientHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .constrainAs(header){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    onBack = onBack

                )

            }
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