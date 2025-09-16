package com.imobly.imobly.ui.screens.show.showproperty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.input.InputComp

import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPropertyScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
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
                                    .background(Color.Red)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black,
                                shape = CircleShape
                            )
                            .padding(8.dp)
                    ) {
                        Text("LOGO", color = Color.White, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {

                Button(
                    modifier = Modifier
                        .width(60.dp)
                        .height(50.dp)
                        .background(
                            Color.Red,
                            shape = RoundedCornerShape(bottomEnd = 20.dp, topEnd = 20.dp)
                        ),
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("<-", color = Color.White, fontSize = 20.sp)
                }

                Box(Modifier.align(Alignment.Center)) {
                    Text("Dados do imóvel", fontSize = 25.sp, color = Color.Gray);
                }
            }

            InputComp(
                label = "Títutlo",
                placeholder = "Ex: Predio em copacabana"
            )

            InputComp(
                label = "Estado",
                placeholder = "Ex: SP"
            )
        }
    }
}

@Composable
@Preview
fun ShowPropertyPreview() {
    val navController = rememberNavController()
    ShowPropertyScreen(navController)
}