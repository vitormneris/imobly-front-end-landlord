package com.imobly.imobly.ui.screens.show.showpayments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Payment
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PaymentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPaymentsScreen(paymentViewModel: PaymentViewModel) {

    val payments = paymentViewModel.payments
    paymentViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(paymentViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            TitleComp("Pagamentos", { paymentViewModel.goToHome() })

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchBarComp(
                    "Buscar pagamento",
                    paymentViewModel.searchText.value,
                    { paymentViewModel.changeSearchText(it) },
                    { paymentViewModel.searchAction() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(payments.value) { payment ->
                    PaymentCard(payment) {
                        paymentViewModel.goToOpen(payment)
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentCard(payment: Payment, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {

        Column(Modifier.padding(20.dp)) {

            Text(
                text = "Valor: R$ ${payment.rentalValue}",
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFont(),
                fontSize = 20.sp,
                color = Color(0xFF2D5B7A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Status: ${payment.status}",
                    fontSize = 14.sp,
                    color = if (payment.status.lowercase() == "pago")
                        Color(0xFF2ECC71)
                    else Color(0xFFF2603F),
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont()
                )

                Text(
                    text = "Vencimento: ${payment.dueDate}",
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    fontFamily = montserratFont()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Emissão: ${payment.issueDate}",
                fontSize = 14.sp,
                color = Color(0xFF777777),
                fontFamily = montserratFont()
            )
        }
    }
}

@Preview
@Composable
fun ShowPaymentsScreenPreview() {
    val nav = rememberNavController()
    ShowPaymentsScreen(PaymentViewModel(nav))
}
