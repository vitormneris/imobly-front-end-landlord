package com.imobly.imobly.ui.screens.edit.editlease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.screens.create.createlease.CreateLeaseScreen
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.LeaseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditLeaseScreen(leaseViewModel: LeaseViewModel) {
    val scrollState = rememberScrollState()

    leaseViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(leaseViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComp(text = "Atualizar locação", buttonBackAction = { leaseViewModel.goToShowLeases() })

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier
                .verticalScroll(scrollState)
                .widthIn(max = 1000.dp)
                .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    InputComp(
                        label = "Dia do vencimento",
                        placeholder = "Ex: 20",
                        value = leaseViewModel.lease.value.paymentDueDay,
                        onValueChange = { leaseViewModel.changePaymentDueDay(it) },
                        isNumeric = true,
                        isError = leaseViewModel.inputContainsError("paymentDueDay"),
                        errorMessage = leaseViewModel.getInputErrorMessage("paymentDueDay"),
                        modifier = Modifier.weight(1f),
                        readOnly = leaseViewModel.inputLockState.value
                    )

                    InputComp(
                        label = "Valor do aluguel",
                        placeholder = "Ex: 2500.00",
                        value = leaseViewModel.lease.value.monthlyRent,
                        onValueChange = { leaseViewModel.changeMonthlyRent(it) },
                        isNumeric = true,
                        isError = leaseViewModel.inputContainsError("monthlyRent"),
                        errorMessage = leaseViewModel.getInputErrorMessage("monthlyRent"),
                        readOnly = leaseViewModel.inputLockState.value,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    InputDateComp(
                        label = "Data de início",
                        value = leaseViewModel.lease.value.startDate,
                        onValueChange = { leaseViewModel.changeStartDate(it) },
                        isError = leaseViewModel.inputContainsError("startDate"),
                        errorMessage = leaseViewModel.getInputErrorMessage("startDate"),
                        readOnly = leaseViewModel.inputLockState.value,
                        modifier = Modifier.weight(1f),
                    )

                    InputDateComp(
                        label = "Data de término",
                        value = leaseViewModel.lease.value.endDate,
                        onValueChange = { leaseViewModel.changeEndDate(it) },
                        isError = leaseViewModel.inputContainsError("endDate"),
                        errorMessage = leaseViewModel.getInputErrorMessage("endDate"),
                        readOnly = leaseViewModel.inputLockState.value,
                        modifier = Modifier.weight(1f)
                    )
                }

                InputComp(
                    label = "Depósito",
                    placeholder = "Ex: 1000.00",
                    value = leaseViewModel.lease.value.securityDeposit,
                    onValueChange = { leaseViewModel.changeSecurityDeposit(it) },
                    isError = leaseViewModel.inputContainsError("securityDeposit"),
                    errorMessage = leaseViewModel.getInputErrorMessage("securityDeposit"),
                    readOnly = leaseViewModel.inputLockState.value,
                    isNumeric = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ConfirmDialogComp(
                leaseViewModel.showDialogState.value,
                if (leaseViewModel.lease.value.isEnabled) "Está ação desabilitará esta locação"
                else "Está ação habilitará esta locação",
                "Tem certeza que deseja continuar?",
                { leaseViewModel.toggleEnableAction() },
                { leaseViewModel.changeShowDialog() }
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (leaseViewModel.messageError.value != "") {
                    MessageErrorComp(leaseViewModel.messageError.value, 14.sp)
                }

                if (leaseViewModel.inputLockState.value) {
                    FlowRow {
                        ButtonComp(
                            "Editar dados",
                            { Icon(Icons.Default.Edit, "editar") },
                            PrimaryColor,
                            { leaseViewModel.hiddenEditButton() },
                            185.dp,
                            16.sp
                        )

                        ButtonComp(
                            if (leaseViewModel.lease.value.isEnabled) "Desabilitar" else "Habilitar",
                            {
                                if (leaseViewModel.lease.value.isEnabled) Icon(Icons.Default.ToggleOff, "desabilitar")
                                else Icon(Icons.Default.ToggleOn, "habilitar")
                            },
                            if (leaseViewModel.lease.value.isEnabled) CancelColor else ConfirmColor,
                            { leaseViewModel.changeShowDialog() },
                            180.dp,
                            16.sp
                        )
                    }

                } else {
                    if (leaseViewModel.onLoadingState.value) {
                        Box(Modifier.padding(20.dp)) {
                            CircularProgressIndicator()
                        }
                    } else {
                        FlowRow {
                            ButtonComp(
                                "Salvar",
                                { Icon(Icons.Default.Check, "confirmar") },
                                ConfirmColor,
                                { leaseViewModel.editAction() },
                                140.dp,
                                16.sp
                            )

                            ButtonComp(
                                "Cancelar",
                                { Icon(Icons.Default.Cancel, "cancelar") },
                                CancelColor,
                                { leaseViewModel.hiddenEditButton() },
                                155.dp,
                                16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
fun EditLeaseScreenPreview() {
    val navControllerFake = rememberNavController()
    CreateLeaseScreen(LeaseViewModel(navControllerFake))
}
