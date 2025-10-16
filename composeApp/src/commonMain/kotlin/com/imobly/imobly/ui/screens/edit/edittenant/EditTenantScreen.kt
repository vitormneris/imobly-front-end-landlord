package com.imobly.imobly.ui.screens.edit.edittenant

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.MaritalStatus
import com.imobly.imobly.domain.Tenant
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.TenantViewModel
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.image_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditTenantScreen(tenantViewModel: TenantViewModel) {
    val scrollState = rememberScrollState()
//    val tenant = remember { mutableStateOf(Tenant()) }
    val tenant = remember { mutableStateOf(
        Tenant(
            name = "João da Silva",
            email = "joao@email.com",
            telephones = arrayOf("11988887777", "1133334444"),
            rg = "12.345.678-9",
            cpf = "123.456.789-00",
            nationality = "Brasileiro",
            maritalStatus = MaritalStatus.MARRIED,
            birthDate = "01/01/1990",
            job = "Engenheiro",
            id = "1",
            property = "Casa 2",
            imageResource = Res.drawable.image_logo
        )
    ) }
    var editing by remember { mutableStateOf(false)}

    Scaffold(
        topBar = {
            TopBarComp()
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComp("Perfil do cliente", {
                tenantViewModel.goToShowTenants()
            })

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painterResource(Res.drawable.image_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color(0xFFE35336), CircleShape)
                )

                InputComp(
                    label = "Nome",
                    placeholder = "Digite o nome...",
                    value = tenant.value.name,
                    onValueChange = { tenant.value = tenant.value.copy(name = it) },
                    readOnly =  !editing
                )

                InputComp(
                    label = "Email",
                    placeholder = "Digite o email...",
                    value = tenant.value.email,
                    onValueChange = { tenant.value = tenant.value.copy(email = it) },
                    readOnly = !editing
                )

                InputComp(
                    label = "Telefone 1",
                    placeholder = "Digite o número...",
                    value = tenant.value.telephones[0],
                    onValueChange = { newValue ->
                        tenant.value = tenant.value.copy(
                            telephones = tenant.value.telephones.copyOf().also { arr ->
                                arr[0] = newValue
                            }
                        )
                    },
                    readOnly = !editing
                )

                InputComp(
                    label = "Telefone 2",
                    placeholder = "Digite o número...",
                    value = tenant.value.telephones[1],
                    onValueChange = { newValue ->
                        tenant.value = tenant.value.copy(
                            telephones = tenant.value.telephones.copyOf().also { arr ->
                                arr[1] = newValue
                            }
                        )
                    },
                    readOnly = !editing
                )

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "RG",
                        placeholder = "Ex: 00.000.000-0",
                        value = tenant.value.rg,
                        onValueChange = { tenant.value = tenant.value.copy(rg = it)},
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp,
                        readOnly = !editing
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "CPF",
                        placeholder = "Ex: 000.000.000-00",
                        value = tenant.value.cpf,
                        onValueChange = { tenant.value = tenant.value.copy(cpf = it) },
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp,
                        readOnly = !editing
                    )
                }

                InputComp(
                    label = "Nacionalidade",
                    placeholder = "Digite a nacionalidade...",
                    value = tenant.value.nationality,
                    onValueChange = { tenant.value = tenant.value.copy(nationality = it) },
                    readOnly = !editing
                )

                DropdownComp(
                    label = "Estado Civil",
                    placeholder = "Escolha",
                    options = MaritalStatus.entries.map { it.label },
                    selectedOption = tenant.value.maritalStatus.label,
                    onOptionSelected = { selectedLabel->
                        tenant.value = tenant.value.copy(maritalStatus = MaritalStatus.entries.first{it.label== selectedLabel}) },
                    isEnabled = editing
                )

                InputComp(
                    label = "Data de nascimento",
                    placeholder = "Digite a data...",
                    value = tenant.value.birthDate,
                    onValueChange = { tenant.value = tenant.value.copy(birthDate = it) },
                    readOnly = !editing
                )

                InputComp(
                    label = "Profissão",
                    placeholder = "Digite a profissão...",
                    value = tenant.value.job,
                    onValueChange = { tenant.value = tenant.value.copy(job = it) },
                    readOnly = !editing
                )


                Box(Modifier.fillMaxWidth().padding(bottom = 16.dp), contentAlignment = Alignment.Center) {
                    if(editing){
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                ButtonComp(
                                    "Salvar",
                                    {  },
                                    ConfirmColor,
                                    {
                                        editing = !editing
                                    },
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                ButtonComp(
                                    "Cancelar",
                                    { },
                                    CancelColor,
                                    {
                                        editing = !editing
                                    },
                                )
                            }
                        }

                    } else{
                        ButtonComp(
                            "Editar dados",
                            {   Icon(Icons.Default.Edit, "") },
                            PrimaryColor,
                            {
                                editing = !editing
                            },
                        )
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun EditTenantScreenPreview() {
    val navControllerFake = rememberNavController()
    EditTenantScreen(TenantViewModel(navControllerFake))
}