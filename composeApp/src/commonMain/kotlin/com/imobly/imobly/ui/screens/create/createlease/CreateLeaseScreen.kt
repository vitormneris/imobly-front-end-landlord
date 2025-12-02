package com.imobly.imobly.ui.screens.create.createlease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputDropdownComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.LeaseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateLeaseScreen(leaseViewModel: LeaseViewModel) {
    val scrollState = rememberScrollState()

    leaseViewModel.whenStartingThePage()
    leaseViewModel.resetPage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(leaseViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComp(text = "Cadastrar Locação", buttonBackAction = { leaseViewModel.goToShowLeases() })

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier
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
                        modifier = Modifier.weight(1f)

                    )

                    InputComp(
                        label = "Valor do aluguel",
                        placeholder = "Ex: 2500.00",
                        value = leaseViewModel.lease.value.monthlyRent,
                        onValueChange = { leaseViewModel.changeMonthlyRent(it) },
                        isNumeric = true,
                        isError = leaseViewModel.inputContainsError("monthlyRent"),
                        errorMessage = leaseViewModel.getInputErrorMessage("monthlyRent"),
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
                        modifier = Modifier.weight(1f) ,
                        readOnly = false
                    )

                    InputDateComp(
                        label = "Data de término",
                        value = leaseViewModel.lease.value.endDate,
                        onValueChange = { leaseViewModel.changeEndDate(it) },
                        isError = leaseViewModel.inputContainsError("endDate"),
                        errorMessage = leaseViewModel.getInputErrorMessage("endDate"),
                        modifier = Modifier.weight(1f),
                        readOnly = false
                    )
                }

                InputComp(
                    label = "Depósito",
                    placeholder = "Ex: 1000.00",
                    value = leaseViewModel.lease.value.securityDeposit,
                    onValueChange = { leaseViewModel.changeSecurityDeposit(it) },
                    isError = leaseViewModel.inputContainsError("securityDeposit"),
                    errorMessage = leaseViewModel.getInputErrorMessage("securityDeposit"),
                    isNumeric = true
                )

                InputDropdownComp(
                    label = "Propriedades",
                    options = leaseViewModel.propertiesOptions(),
                    selectedOption = leaseViewModel.propertySelected.value.title,
                    onOptionSelected = { leaseViewModel.onOptionSelectedProperty(it) },
                    isError = leaseViewModel.inputContainsError("propertyId"),
                    errorMessage = leaseViewModel.getInputErrorMessage("propertyId"),
                )

                InputDropdownComp(
                    label = "Locatários",
                    options = leaseViewModel.tenantsOptions(),
                    selectedOption = leaseViewModel.tenantSelected.value.firstName,
                    onOptionSelected = { leaseViewModel.onOptionSelectedTenant(it) },
                    isError = leaseViewModel.inputContainsError("tenantId"),
                    errorMessage = leaseViewModel.getInputErrorMessage("tenantId"),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (leaseViewModel.onLoadingState.value) {
                    Box(Modifier.padding(20.dp)) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (leaseViewModel.messageError.value != "") {
                        MessageErrorComp(leaseViewModel.messageError.value, 14.sp)
                    }
                    Box(Modifier.align(Alignment.CenterHorizontally)) {
                        ButtonComp(
                            "Registrar contrato",
                            { Icon(Icons.Default.Create, "check") },
                            PrimaryColor,
                            { leaseViewModel.createAction() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
fun PreviewCreateLeaseScreen() {
    val navControllerFake = rememberNavController()
    CreateLeaseScreen(LeaseViewModel(navControllerFake))
}
