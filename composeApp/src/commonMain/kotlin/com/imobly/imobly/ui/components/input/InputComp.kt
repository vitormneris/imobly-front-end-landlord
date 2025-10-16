package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputComp(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isNumeric: Boolean = false,
    fractionWidth: Float = 0.8f,
    maxWidth: Dp = 1000.dp,
    singleLine: Boolean = true,
    readOnly: Boolean = false
) {
    var numLines = 1
    if (!singleLine) {
        numLines = 4
    }

    OutlinedTextField(
        label = {
            Text(label, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        singleLine = singleLine,
        minLines = numLines,
        maxLines = numLines,
        keyboardOptions = KeyboardOptions(keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text),
        placeholder = {
            Text(placeholder, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        },
        textStyle = TextStyle(fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(vertical= 10.dp)
            .widthIn(max = maxWidth)
            .fillMaxWidth(fractionWidth),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = PrimaryColor,
            unfocusedIndicatorColor = PrimaryColor,
            unfocusedContainerColor = BackGroundColor,
            focusedContainerColor = BackGroundColor,
            focusedTextColor = Color.Black,
            focusedLabelColor = PrimaryColor,
            focusedIndicatorColor = PrimaryColor,
            cursorColor = PrimaryColor
        ),
        readOnly = readOnly
    )
}

@Preview
@Composable
fun InputCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InputComp(
            "Example",
            "Text for example",
            "",
            {}
        )
    }
}