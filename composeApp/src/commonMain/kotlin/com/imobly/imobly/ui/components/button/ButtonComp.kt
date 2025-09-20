package com.imobly.imobly.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import com.imobly.imobly.ui.theme.colors.IconColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.ui.theme.icons.EditSquareIcon
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ButtonComp(text: String, icon: @Composable () -> Unit, color: Color, action: () -> Unit) {
    Box(
        Modifier.padding(vertical = 30.dp)
    ) {
        Button(
            onClick = action,
            modifier = Modifier
                .padding(10.dp)
                .widthIn(max = 300.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(color),
            shape = RoundedCornerShape(20.dp)
        ) {
            icon()
            Spacer(Modifier.size(10.dp))
            Text(
                text,
                fontSize = 23.sp,
                fontFamily = montserratFont(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ButtonCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ButtonComp(
            "Exemplo",
            { EditSquareIcon(32.dp, "Edit square", IconColor) },
            PrimaryColor,
            {}
        )
    }
}