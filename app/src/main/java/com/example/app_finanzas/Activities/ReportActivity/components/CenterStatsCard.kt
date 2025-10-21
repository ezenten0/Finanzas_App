package com.example.app_finanzas.Activities.ReportActivity.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.app_finanzas.R

@Composable
fun CenterStatsCard(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier=modifier
        .background(Color.White,shape = RoundedCornerShape(24.dp))
        .border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(24.dp)
        )
        .padding(12.dp)
    ){
        val(progressbar,totalTxt,totalLabel,
            incomeIcon,incomeLabel,incomeValue,
            expenseIcon,expenseLabel,expenseValue)=createRefs()
        CircularProgressbar(
            progress=60f,
            max=100f,
            color=colorResource(R.color.blue),
            backgroundColor = colorResource(R.color.lightGrey),
            stroke = 15.dp,
            modifier=Modifier
                .size(175.dp)
                .constrainAs(progressbar){
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }

        )
        Text("$3.451.200",
            color = colorResource(R.color.blue),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier=Modifier.constrainAs(totalTxt){
                 centerTo(progressbar)
            }
        )
        Text("Total",color = colorResource(R.color.blue),
            modifier = Modifier.constrainAs(totalLabel){
                start.linkTo(totalTxt.start)
                top.linkTo(totalTxt.bottom)
                end.linkTo(totalTxt.end)
            }
        )
        Image(
            painterResource(R.drawable.income),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(incomeIcon) {
                    top.linkTo(progressbar.bottom, margin = 16.dp)
                    start.linkTo(parent.start,margin = 32.dp)
                }
        )
        Text("Ingresos",
            color = colorResource(R.color.darkBlue),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(incomeLabel){
                start.linkTo(incomeIcon.end,margin = 16.dp)
                top.linkTo(incomeIcon.top)
                bottom.linkTo(incomeIcon.bottom)
            }
        )
        Text("$2.500.000",
            color = colorResource(R.color.darkBlue),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .constrainAs(incomeValue){
                    start.linkTo(incomeLabel.start)
                    top.linkTo(incomeLabel.bottom)
                    end.linkTo(incomeLabel.end)
                }
        )

        Image(
            painterResource(R.drawable.expense),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(expenseIcon) {
                    top.linkTo(expenseLabel.top)
                    end.linkTo(expenseLabel.start,margin = 8.dp)
                    bottom.linkTo(expenseLabel.bottom)
                }
        )
        Text("Gastos",
            color = colorResource(R.color.darkBlue),
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            modifier = Modifier.constrainAs(expenseLabel){
                end.linkTo(parent.end,margin = 32.dp)
                top.linkTo(incomeLabel.top)
                bottom.linkTo(incomeLabel.bottom)
            }
        )
        Text("$2.000.000",
            color = colorResource(R.color.purple_700),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .constrainAs(expenseValue){
                    start.linkTo(expenseLabel.start)
                    top.linkTo(expenseLabel.bottom)
                    end.linkTo(expenseLabel.end)
                }
        )
    }


}

@Preview
@Composable
fun CenterStatPreview() {
    CenterStatsCard()
}
