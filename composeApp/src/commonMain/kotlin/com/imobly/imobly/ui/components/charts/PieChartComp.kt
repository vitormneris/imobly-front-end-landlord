package com.imobly.imobly.ui.components.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun PieChartComp(
    modifier: Modifier = Modifier,
    data: List<Float>,
    labels: List<String>
) {
    val customColors = if (labels.contains("PAID")){
        listOf(
            Color(0xFF3caea3),
            CancelColor.copy(alpha = 0.9f),
        )
    } else {
        listOf(
            Color(0xFF3caea3),
            Color(0xFF20639b),
        )
    }

    val cleanedLabels = labels.map { label ->
        when (label) {
            "PAID" -> "Pago"
            "NOT_PAID" -> "Não pago"
            "RENTED" -> "Alugado"
            "NOT_RENTED" -> "Não alugado"
            else -> label
        }
    }


    Box(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                BackGroundColor.copy(
                    1f
                )
            )
            .height(300.dp)
            .padding(20.dp),

        ) {

        PieChart(
            slice = { i->
                DefaultSlice(customColors[i], gap = 0.8f,antiAlias = true,  )
            },
            forceCenteredPie = true,
            modifier = Modifier
                .fillMaxSize(),
            values = data,
            label = { i ->
                Text(
                    fontFamily = montserratFont(),
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    modifier = Modifier,
                    text= cleanedLabels[i])
            }
        )
    }
}