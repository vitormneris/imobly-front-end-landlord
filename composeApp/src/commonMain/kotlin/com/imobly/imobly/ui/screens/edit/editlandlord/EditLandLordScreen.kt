package com.imobly.imobly.ui.screens.edit.editlandlord

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.LandLordViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditLandLordScreen(landLordViewModel: LandLordViewModel) {
    val scrollState = rememberScrollState()

    landLordViewModel.whenStartingThePage()
    landLordViewModel.findProfile()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = { SnackbarHost( landLordViewModel.snackMessage.value ) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComp("Seu perfil", {
                landLordViewModel.goToHome()
            })

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputComp(
                    label = "Nome",
                    placeholder = "João",
                    value = landLordViewModel.landLord.value.firstName,
                    onValueChange = { landLordViewModel.changeFirstName(it) },
                    readOnly = landLordViewModel.inputLockState.value,
                    isError = landLordViewModel.inputContainsError("firstName"),
                    errorMessage = landLordViewModel.getInputErrorMessage("firstName")
                )

                InputComp(
                    label = "Sobrenome",
                    placeholder = "Silva",
                    value = landLordViewModel.landLord.value.lastName,
                    onValueChange = { landLordViewModel.changeLastName(it) },
                    readOnly = landLordViewModel.inputLockState.value,
                    isError = landLordViewModel.inputContainsError("lastName"),
                    errorMessage = landLordViewModel.getInputErrorMessage("lastName")
                )

                InputComp(
                    label = "Email",
                    placeholder = "joao@dominio.com",
                    value = landLordViewModel.landLord.value.email,
                    onValueChange = { landLordViewModel.changeEmail(it) },
                    readOnly = landLordViewModel.inputLockState.value,
                    isError = landLordViewModel.inputContainsError("email"),
                    errorMessage = landLordViewModel.getInputErrorMessage("email")
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputComp(
                        label = "Telefone 1",
                        placeholder = "Ex: (00) 00000-0000",
                        value = landLordViewModel.landLord.value.telephones.telephone1,
                        onValueChange = { landLordViewModel.changeTelephoneOne(it) },
                        readOnly = landLordViewModel.inputLockState.value,
                        isError = (landLordViewModel.inputContainsError("telephones") ||
                                landLordViewModel.inputContainsError("telephones.telephone1")),
                        errorMessage = if (landLordViewModel.inputContainsError("telephones")) {
                            landLordViewModel.getInputErrorMessage("telephones")
                        } else {
                            landLordViewModel.getInputErrorMessage("telephones.telephone1")
                        },
                        modifier = Modifier.weight(1f)

                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Telefone 2",
                        placeholder = "Ex: (00) 00000-0000",
                        value = landLordViewModel.landLord.value.telephones.telephone2 ?: "",
                        onValueChange = { landLordViewModel.changeTelephoneTwo(it) },
                        readOnly = landLordViewModel.inputLockState.value,
                        isError = landLordViewModel.inputContainsError("telephones.telephone2"),
                        errorMessage = landLordViewModel.getInputErrorMessage("telephones.telephone2"),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputComp(
                        label = "Telefone 3",
                        placeholder = "Ex: (00) 00000-0000",
                        value = landLordViewModel.landLord.value.telephones.telephone3 ?: "",
                        onValueChange = { landLordViewModel.changeTelephoneThree(it) },
                        readOnly = landLordViewModel.inputLockState.value,
                        isError = landLordViewModel.inputContainsError("telephones.telephone3"),
                        errorMessage = landLordViewModel.getInputErrorMessage("telephones.telephone3"),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.size(60.dp).weight(1f))
                }

                ConfirmDialogComp(
                    landLordViewModel.showDialogState.value,
                    "Está ação deletará seu perfil",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { landLordViewModel.deleteAction() },
                    { landLordViewModel.changeShowDialog() }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (landLordViewModel.messageError.value != "") {
                        MessageErrorComp(landLordViewModel.messageError.value, 16.sp)
                    }
                    if (landLordViewModel.inputLockState.value) {

                        FlowRow {
                            ButtonComp(
                                "Editar dados",
                                { Icon(Icons.Default.Edit, "editar") },
                                PrimaryColor,
                                { landLordViewModel.hiddenEditButton() },
                                185.dp,
                                16.sp
                            )

                            ButtonComp(
                                "Excluir",
                                { Icon(Icons.Default.Delete, "deletar") },
                                CancelColor,
                                { landLordViewModel.changeShowDialog() },
                                140.dp,
                                16.sp
                            )
                        }

                    } else {
                        if (landLordViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {
                            FlowRow {
                                ButtonComp(
                                    "Salvar",
                                    { Icon(Icons.Default.Check, "confirmar") },
                                    ConfirmColor,
                                    { landLordViewModel.editAction() },
                                    140.dp,
                                    16.sp
                                )

                                ButtonComp(
                                    "Cancelar",
                                    { Icon(Icons.Default.Cancel, "cancelar") },
                                    CancelColor,
                                    { landLordViewModel.hiddenEditButton() },
                                    155.dp,
                                    16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EditLandLordScreenPreview() {
    val navControllerFake = rememberNavController()
    EditLandLordScreen(LandLordViewModel(navControllerFake))
}