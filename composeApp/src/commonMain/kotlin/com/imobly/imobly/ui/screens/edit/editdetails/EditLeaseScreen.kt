package com.imobly.imobly.ui.screens.edit.editdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditLeaseScreen(
    onBack: () -> Unit = {},
    onSaved: () -> Unit = {}
) {
    var rentValue by remember { mutableStateOf("1200.00") }
    var endDate by remember { mutableStateOf("31/12/2025") }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBarComp() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TitleComp(text = "Editar Locação", buttonBackAction = onBack)

            InputComp(
                label = "Valor do Aluguel",
                placeholder = "Ex: 2500.00",
                value = rentValue,
                onValueChange = { rentValue = it },
                isNumeric = true
            )

            InputComp(
                label = "Data de Término",
                placeholder = "Ex: 31/12/2025",
                value = endDate,
                onValueChange = { endDate = it }
            )

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                if (errorMessage.isNotEmpty()) {
                    MessageErrorComp(errorMessage)
                }

                ButtonComp(
                    text = "Salvar Alterações",
                    icon = { androidx.compose.material3.Icon(Icons.Default.Check, contentDescription = "Salvar") },
                    color = PrimaryColor
                ) {
                    if (rentValue.isBlank() || endDate.isBlank()) {
                        errorMessage = "Preencha todos os campos obrigatórios"
                        return@ButtonComp
                    }

                    isLoading = true
                    errorMessage = ""

                    coroutineScope.launch {
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
fun PreviewEditLeaseScreen() {
    EditLeaseScreen(onBack = {}, onSaved = {})
}
