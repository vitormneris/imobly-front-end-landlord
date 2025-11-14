package com.imobly.imobly.ui.components.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import io.github.koalaplot.core.bar.DefaultVerticalBar
import io.github.koalaplot.core.bar.VerticalBarPlot
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.rememberFloatLinearAxisModel

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun BarPlotComp(
    modifier: Modifier = Modifier,
    xData: List<String>,
    yData: List<Float>,

    ){

    val highestValue = yData.max()+3000f
    val lowestValue = maxOf(yData.min()-6000f,0f)

    val cleanedXData = xData.map { data->
        when(data){
            "JANUARY"->"Jan"
            "FEBRUARY"->"Fev"
            "MARCH"->"Mar"
            "APRIL"->"Abr"
            "MAY"->"Maio"
            "JUNE"->"Jun"
            "JULY"->"Jul"
            "AUGUST"->"Ago"
            "SEPTEMBER"->"Set"
            "OCTOBER"->"Out"
            "NOVEMBER"->"Nov"
            "DECEMBER"->"Dez"
            else -> data
        }
    }




    XYGraph(
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
        xAxisModel = remember { CategoryAxisModel(cleanedXData) },
        yAxisModel = rememberFloatLinearAxisModel(lowestValue..highestValue, minimumMajorTickSpacing = 30.dp, minorTickCount = 0),
        yAxisLabels = {value ->
            val number = value.toInt()
            number.toString()
                .reversed()
                .chunked(3)
                .joinToString(".")
                .reversed()
        }

    ) {
        VerticalBarPlot(
            modifier = Modifier
                .fillMaxSize(),
            xData = cleanedXData,
            yData = yData,
            bar = {
                DefaultVerticalBar(SolidColor(Color(0xFF20639b)))
            }
        )
    }

}