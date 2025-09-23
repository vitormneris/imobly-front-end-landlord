package com.imobly.imobly.ui.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.imobly
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBarComp() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .width(20.dp)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        // Menu hamburguer (3 traços)
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            repeat(3) {
                Box(modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp)) {
                    Box(
                        Modifier
                            .width(35.dp)
                            .height(5.dp)
                            .background(
                                Color(0xFFF2603F),
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }

        // Logo centralizada
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.imobly),
                contentDescription = "Imobly Logo",
                modifier = Modifier
                    .width(130.dp) // largura maior
                    .height(50.dp), // altura maior
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview
@Composable
fun TopBarCompPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        TopBarComp()
    }
}