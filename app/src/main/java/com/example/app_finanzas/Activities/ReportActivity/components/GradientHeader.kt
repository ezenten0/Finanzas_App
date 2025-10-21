package com.example.app_finanzas.Activities.ReportActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.app_finanzas.R


@Composable
fun GradientHeader(modifier: Modifier = Modifier, onBack: () -> Unit) {
    ConstraintLayout (modifier = modifier
        .background(
            Brush.linearGradient(
                colors = listOf(colorResource(R.color.purple_200), colorResource(R.color.purple_700)),
                start = Offset(0f,0f),
                end = Offset(1000f,600f)
            ),
            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
        )
    ){
        val(backBtn,title) = createRefs()
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(48.dp)
                .padding(16.dp)
                .constrainAs(backBtn) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 48.dp)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = null,
                tint = Color.White

            )
        }
        Text(
            "Finanzas Personales",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier= Modifier
                .constrainAs(title) {
                    start.linkTo(backBtn.end)
                    top.linkTo(backBtn.top)
                    bottom.linkTo(backBtn.bottom)
                }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GradientHeaderPreview() {
    GradientHeader(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        onBack = {}
    )
}

