package com.imobly.imobly.ui.components.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComp(
    label: String,
    placeholder: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    fractionWidth: Float = 0.8f,
    maxWidth: Dp = 1000.dp,
    isEnabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (isEnabled) expanded = !expanded
        }

    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    label,
                    fontFamily = montserratFont(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            placeholder = {
                Text(
                    placeholder,
                    fontFamily = montserratFont(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontFamily = montserratFont(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .widthIn(max = maxWidth)
                .fillMaxWidth(fractionWidth)
                .menuAnchor(),
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
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded && isEnabled,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            fontFamily = montserratFont(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var selected by remember { mutableStateOf("SINGLE") }
        DropdownComp(
            label = "Estado Civil",
            placeholder = "Selecione...",
            options = listOf("MARRIED", "SINGLE", "WIDOWED", "DIVORCED"),
            selectedOption = selected,
            onOptionSelected = { selected = it }
        )
    }
}