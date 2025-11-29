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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.MonthlyInstallment
import com.imobly.imobly.domain.Payment
import com.imobly.imobly.domain.enums.PaymentStatusEnum
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PaymentViewModel
import com.imobly.imobly.viewmodel.SharedRepository
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPaymentsScreen(paymentViewModel: PaymentViewModel) {

    paymentViewModel.payment.value =  SharedRepository.payment ?: Payment()

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

            TitleComp("Pagamentos", { paymentViewModel.goToShowLeases() }, true)

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

                items(paymentViewModel.payment.value.installments) { installment ->
                    PaymentCard(
                        installment = installment,
                        onClick = { paymentViewModel.goToInstallment(installment) }
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentCard(installment: MonthlyInstallment, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(Modifier.padding(20.dp)) {

            Text(
                text = "Valor: R$ ${installment.monthlyRent}",
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
                    text = "Status: ${installment.status}",
                    fontSize = 14.sp,
                    color = when (installment.status) {
                        PaymentStatusEnum.PAID -> ConfirmColor
                        PaymentStatusEnum.PENDING -> Color(0xFFF2603F)
                        else -> CancelColor
                    },
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont()
                )

                Text(
                    text = "Vencimento: ${installment.dueDate}",
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    fontFamily = montserratFont()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Mês: ${installment.month}",
                fontSize = 14.sp,
                color = Color(0xFF777777),
                fontFamily = montserratFont()
            )
        }
    }
}

@Preview
@Composable
fun ShowPaymentsPreview() {
    val navFake = rememberNavController()
    ShowPaymentsScreen(PaymentViewModel(navFake))
}
