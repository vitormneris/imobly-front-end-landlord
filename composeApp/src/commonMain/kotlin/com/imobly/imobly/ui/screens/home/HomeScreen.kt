package com.imobly.imobly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
            modifier = Modifier
                .background(BackGroundColor)
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Tela home provisória",
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = montserratFont()
        )
        Button(
            onClick = { navController.navigate("showproperty") }
        ) {
            Text("Tela home provisória", fontSize = 30.sp, color = Color.Red)
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { navController.navigate("showproperty") }
            ) {
                Text("Go to showproperty", fontSize = 20.sp, color = Color.White)
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("properties") }
            ) {
                Text("Ir para PropertiesScreen", fontSize = 20.sp, color = Color.White)
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("properties") }
        ) {
            Text("Ir para PropertiesScreen", fontSize = 20.sp, color = Color.White)
        }
        Spacer(Modifier.size(30.dp))
        ButtonComp(
            "Go to showproperty",
            {},
            PrimaryColor,
            { navController.navigate("createproperty") }
        )
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}