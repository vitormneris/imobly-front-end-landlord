package com.imobly.imobly.ui.screens.create.createLease

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CreateLeaseScreen(
    onBack: () -> Unit = {},
    onSaved: () -> Unit = {}
) {

    var paymentDueDay by remember { mutableStateOf("") }
    var monthlyRent by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var securityDeposit by remember { mutableStateOf("") }


    val properties = listOf("Apartamento 101", "Casa Verde", "Studio Centro")
    val tenants = listOf("João Silva", "Maria Souza", "Carlos Lima")

    var selectedProperty by remember { mutableStateOf(properties.first()) }
    var selectedTenant by remember { mutableStateOf(tenants.first()) }


    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(hostState = androidx.compose.material3.SnackbarHostState()) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleComp(text = "Cadastrar Locação", buttonBackAction = onBack)

            Spacer(modifier = Modifier.height(8.dp))
            
            InputComp(
                label = "Dia do vencimento",
                placeholder = "Ex: 14/12/2025",
                value = paymentDueDay,
                onValueChange = { paymentDueDay = it },
                isNumeric = true
            )

            InputComp(
                label = "Valor do aluguel",
                placeholder = "Ex: 2500.00",
                value = monthlyRent,
                onValueChange = { monthlyRent = it },
                isNumeric = true
            )

            InputDateComp(
                label = "Data de início",
                value = startDate,
                onValueChange = { startDate = it }
            )

            InputDateComp(
                label = "Data de término",
                value = endDate,
                onValueChange = { endDate = it }
            )

            InputComp(
                label = "Depósito",
                placeholder = "Ex: 1000.00",
                value = securityDeposit,
                onValueChange = { securityDeposit = it },
                isNumeric = true
            )

            DropdownComp(
                label = "Propriedade",
                placeholder = "Selecione a propriedade",
                options = properties,
                selectedOption = selectedProperty,
                onOptionSelected = { selectedProperty = it }
            )

            DropdownComp(
                label = "Locatário",
                placeholder = "Selecione o locatário",
                options = tenants,
                selectedOption = selectedTenant,
                onOptionSelected = { selectedTenant = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                if (errorMessage.isNotEmpty()) {
                    MessageErrorComp(errorMessage)
                }

                ButtonComp(
                    "Salvar Locação",
                    icon = { Icon(Icons.Default.Check, contentDescription = "Salvar") },
                    color = PrimaryColor
                ) {
                    if (monthlyRent.isBlank() || startDate.isBlank() || endDate.isBlank()) {
                        errorMessage = "Preencha os campos obrigatórios"
                        return@ButtonComp
                    }

                    isLoading = true
                    errorMessage = ""

                    coroutineScope.launch(Dispatchers.Main) {
                        delay(700)
                        isLoading = false
                        onSaved()
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateLeaseScreen() {
    CreateLeaseScreen(onBack = {}, onSaved = {})
}
