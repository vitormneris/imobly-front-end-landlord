package com.imobly.imobly.ui.screens.open.openpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.PaymentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OpenPaymentScreen(paymentViewModel: PaymentViewModel) {

    val scrollState = rememberScrollState()

    paymentViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(paymentViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleComp("Editar Pagamento", { paymentViewModel.goToHome() })

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputComp(
                    label = "Data de Emissão",
                    placeholder = "01/01/2025",
                    value = paymentViewModel.payment.value.issueDate,
                    onValueChange = { paymentViewModel.changeIssueDate(it) },
                    isError = paymentViewModel.inputContainsError("issueDate"),
                    errorMessage = paymentViewModel.getInputErrorMessage("issueDate")
                )

                Spacer(modifier = Modifier.height(20.dp))

                InputComp(
                    label = "Data de Vencimento",
                    placeholder = "10/01/2025",
                    value = paymentViewModel.payment.value.dueDate,
                    onValueChange = { paymentViewModel.changeDueDate(it) },
                    isError = paymentViewModel.inputContainsError("dueDate"),
                    errorMessage = paymentViewModel.getInputErrorMessage("dueDate")
                )

                Spacer(modifier = Modifier.height(20.dp))

                InputComp(
                    label = "Valor",
                    placeholder = "1500.00",
                    value = paymentViewModel.payment.value.rentalValue.toString(),
                    onValueChange = { paymentViewModel.changeRentalValue(it) },
                    isError = paymentViewModel.inputContainsError("rentalValue"),
                    errorMessage = paymentViewModel.getInputErrorMessage("rentalValue")
                )

                Spacer(modifier = Modifier.height(20.dp))

                InputComp(
                    label = "Status",
                    placeholder = "Pago / Pendente",
                    value = paymentViewModel.payment.value.status,
                    onValueChange = { paymentViewModel.changeStatus(it) },
                    isError = paymentViewModel.inputContainsError("status"),
                    errorMessage = paymentViewModel.getInputErrorMessage("status")
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (paymentViewModel.onLoadingState.value) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                } else {

                    if (paymentViewModel.messageError.value.isNotEmpty()) {
                        MessageErrorComp(
                            message = paymentViewModel.messageError.value,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    ButtonComp(
                        text = "Atualizar Pagamento",
                        icon = { Icon(Icons.Default.Create, contentDescription = null) },
                        color = PrimaryColor,
                        action = { paymentViewModel.updateAction() }
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun OpenPaymentScreenPreview() {
    val nav = rememberNavController()
    OpenPaymentScreen(PaymentViewModel(nav))
}