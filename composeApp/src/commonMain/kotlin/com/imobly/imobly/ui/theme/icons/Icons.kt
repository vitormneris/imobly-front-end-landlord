package com.imobly.imobly.ui.theme.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.icon_arrow_back
import imobly.composeapp.generated.resources.icon_edit_square
import imobly.composeapp.generated.resources.icon_check
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource



@Composable
fun ArrowBackIcon(size: Dp, iconDescription: String, color: Color) {
    Icon(
        painter = painterResource(Res.drawable.icon_arrow_back),
        contentDescription = iconDescription,
        modifier = Modifier.size(size),
        tint = color
    )
}

@Composable
fun EditSquareIcon(size: Dp, iconDescription: String, color: Color) {
    Icon(
        painter = painterResource(Res.drawable.icon_edit_square),
        contentDescription = iconDescription,
        modifier = Modifier.size(size),
        tint = color
    )
}


@Composable
fun CheckIcon(size: Dp, iconDescription: String, color: Color) {
    Icon(
        painter = painterResource(Res.drawable.icon_check),
        contentDescription = iconDescription,
        modifier = Modifier.size(size),
        tint = color
    )
}
