package com.imobly.imobly.ui.components.title

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.ui.theme.icons.ArrowBackIcon
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TitleComp(text: String, buttonBackAction: () -> Unit) {

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
                    PrimaryColor,
                    shape = RoundedCornerShape(bottomEnd = 25.dp, topEnd = 25.dp)
                ),
            onClick = buttonBackAction,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            ArrowBackIcon(35.dp, "back button", BackGroundColor)
        }

        Box(Modifier.align(Alignment.Center)) {
            Text(
                text,
                fontSize = 20.sp,
                color = TitleColor,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = montserratFont()
            )
        }
    }
}

@Preview
@Composable
fun TitleCompPreview() {
    Box(
        Modifier.fillMaxSize().background(BackGroundColor),
        contentAlignment = Alignment.TopCenter
    ) {
        TitleComp("Exemplo", {})
    }
}