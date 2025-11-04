package com.imobly.imobly.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.domain.ReportStatus
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReportCardComp(
    title: String,
    description: String,
    moment: String,
    status: String,
    response: String,
    tenant: String,
    action: () -> Unit
) {
    val statusColor:Color = when (status){
        ReportStatus.NEW.description -> Color(0xFF0059ff)
        ReportStatus.PENDING.description-> Color(0xFFFFC107)
        ReportStatus.RESOLVED.description-> ConfirmColor
        else -> CancelColor
    }


//    Color(0xFFFFC107)pending
//    Color(0xFF4CAF50)closed
    Card(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                title,
                fontFamily = montserratFont(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )
            Text(
                description,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                fontFamily = montserratFont(),
                fontSize = 13.sp,
                color = Color(0xFF555555)
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🕒 $moment",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = montserratFont()
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = montserratFont()
                    )
                }
            }

            if (response.isNotBlank()) {
                Text(
                    "Resposta:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = montserratFont()
                )
                Text(
                    response,
                    fontSize = 12.sp,
                    color = Color(0xFF444444),
                    fontFamily = montserratFont()
                )
            }

            Text(
                "Inquilino: $tenant",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = montserratFont()
            )
        }
    }
}


@Composable
@Preview
fun PreviewReportCardComp(){
    Box(
        Modifier.fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ){

        ReportCardComp(
            "Há goteiras no meu quarto",
            "Quando chove fica pingando bem em cima do guarda roupas que fica do lado da porta do banheiro.",
            "29/09/2025 às 13:47",
            "NEW",
            "Podemos agendar a visita de um pedreiro para resolver isso, vou te chamar no Whatsapp para podermos conversar melhor.",
            "Roberto Durval Santos",
            {}
        )

    }

}