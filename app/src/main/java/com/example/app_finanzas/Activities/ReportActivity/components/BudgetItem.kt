package com.example.app_finanzas.Activities.ReportActivity.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.R

@Composable
fun BudgetItem(budget: BudgetDomain,index:Int){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
    ){
        val(progress,title,price,percent)=createRefs()

        CircularProgressbar(
            progress = budget.percent.toFloat(),
            max = 100f,
            color = if((index%2)==1) colorResource(R.color.blue) else colorResource(R.color.pink),
            backgroundColor = colorResource(R.color.lightGrey),
            stroke = 7.dp,
            modifier = Modifier
                .size(80.dp)
                .constrainAs(progress) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            budget.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(progress.end, margin = 8.dp)
            }
        )
        Text("$${DecimalFormat( "###,###,###,###").format(budget.price)} /mensuales",
        color = colorResource(R.color.grey),
            modifier = Modifier.constrainAs(price){
                top.linkTo(title.bottom)
                start.linkTo(title.start)
                bottom.linkTo(progress.bottom)

            }
        )
        Text("%${budget.percent }",
            color = if((index%2)==1) colorResource(R.color.blue) else colorResource(R.color.pink),
            modifier = Modifier.constrainAs(percent){
                centerTo(progress)
            }
        )
    }
}




@Preview
@Composable
fun BudgetItemPreview() {
    val budget = BudgetDomain(title = "Comida",price = 100.0,percent = 0.5)
    BudgetItem(budget = budget,index = 0)

}