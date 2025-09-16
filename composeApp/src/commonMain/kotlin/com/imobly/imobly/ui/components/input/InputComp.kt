package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.fonts.montserrat
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputComp(label: String, placeholder: String) {
    var text by remember {  mutableStateOf("") }

    OutlinedTextField(
        label = {
            Text(label, fontFamily = montserrat(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        },
        onValueChange = { newText -> text = newText },
        value = text,
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        placeholder = {
            Text(placeholder, fontFamily = montserrat(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        },
        textStyle = TextStyle(fontFamily = montserrat(), fontSize = 15.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(vertical= 10.dp)
            .widthIn(max = 1000.dp)
            .fillMaxWidth(0.8f),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            focusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Preview
@Composable
fun InputCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InputComp("Example", "Text for example")
    }
}