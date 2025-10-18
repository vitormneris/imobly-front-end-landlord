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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.MaritalStatus
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.TenantViewModel
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.image_logo
import io.github.ismoy.imagepickerkmp.domain.extensions.loadPainter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditTenantScreen(tenantViewModel: TenantViewModel) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = { SnackbarHost( tenantViewModel.snackMessage.value ) },
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

                if (tenantViewModel.selectedImages.value.isEmpty()) {
                    KamelImage(
                        resource = { asyncPainterResource(tenantViewModel.tenant.value.pathImage) },
                        contentDescription = "Imagem do locatário ${tenantViewModel.tenant.value.firstName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFFE35336), CircleShape),
                        onLoading = { progress -> CircularProgressIndicator({ progress }) },
                        onFailure = { CircularProgressIndicator() }
                    )
                } else {
                    tenantViewModel.selectedImages.value.first().loadPainter()?.let {
                        Image(
                            painter = it,
                            contentDescription = "Imagem do locatário selecionada",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                                .border(4.dp, Color(0xFFE35336), CircleShape),
                        )
                    }
                }

                if (!tenantViewModel.inputLockState.value) {
                    ImagePickerComp("Escolha as imagens", tenantViewModel.selectedImages)
                }

                InputComp(
                    label = "Nome",
                    placeholder = "João",
                    value = tenantViewModel.tenant.value.firstName,
                    onValueChange = { tenantViewModel.changeFirstName(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("firstName"),
                    errorMessage = tenantViewModel.getInputErrorMessage("firstName")
                )

                InputComp(
                    label = "Sobrenome",
                    placeholder = "Silva",
                    value = tenantViewModel.tenant.value.lastName,
                    onValueChange = { tenantViewModel.changeLastName(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("lastName"),
                    errorMessage = tenantViewModel.getInputErrorMessage("lastName")
                )

                InputComp(
                    label = "Email",
                    placeholder = "joao@dominio.com",
                    value = tenantViewModel.tenant.value.email,
                    onValueChange = { tenantViewModel.changeEmail(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("email"),
                    errorMessage = tenantViewModel.getInputErrorMessage("email")
                )

                InputComp(
                    label = "Telefone 1",
                    placeholder = "(00) 00000-0000",
                    value = tenantViewModel.telephoneOne.value,
                    onValueChange = { tenantViewModel.changeTelephoneOne(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = (tenantViewModel.inputContainsError("telephones") ||
                            tenantViewModel.inputContainsError("telephones[0].telephone")),
                    errorMessage = if (tenantViewModel.inputContainsError("telephones")) {
                        tenantViewModel.getInputErrorMessage("telephones")
                    } else {
                        tenantViewModel.getInputErrorMessage("telephones[0].telephone")
                    }
                )

                InputComp(
                    label = "Telefone 2",
                    placeholder = "(00) 00000-0000",
                    value = tenantViewModel.telephoneTwo.value,
                    onValueChange = { tenantViewModel.changeTelephoneTwo(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("telephones[1].telephone"),
                    errorMessage = tenantViewModel.getInputErrorMessage("telephones[1].telephone")
                )

                InputComp(
                    label = "Telefone 3",
                    placeholder = "(00) 00000-0000",
                    value = tenantViewModel.telephoneThree.value,
                    onValueChange = { tenantViewModel.changeTelephoneThree(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("telephones[2].telephone"),
                    errorMessage = tenantViewModel.getInputErrorMessage("telephones[2].telephone")
                )

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "RG",
                        placeholder = "Ex: 00.000.000-0",
                        value = tenantViewModel.tenant.value.rg,
                        onValueChange = { tenantViewModel.changeCpf(it) },
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp,
                        readOnly = tenantViewModel.inputLockState.value,
                        isError = tenantViewModel.inputContainsError("rg"),
                        errorMessage = tenantViewModel.getInputErrorMessage("rg")
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "CPF",
                        placeholder = "Ex: 000.000.000-00",
                        value = tenantViewModel.tenant.value.cpf,
                        onValueChange = { tenantViewModel.changeCpf(it) },
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp,
                        readOnly = tenantViewModel.inputLockState.value,
                        isError = tenantViewModel.inputContainsError("cpf"),
                        errorMessage = tenantViewModel.getInputErrorMessage("cpf")
                    )
                }

                InputComp(
                    label = "Nacionalidade",
                    placeholder = "Digite a nacionalidade...",
                    value = tenantViewModel.tenant.value.nationality,
                    onValueChange = { tenantViewModel.changeNationality(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("nationality"),
                    errorMessage = tenantViewModel.getInputErrorMessage("nationality")
                )

                DropdownComp(
                    label = "Estado Civil",
                    placeholder = "Escolha",
                    options = MaritalStatus.entries.map { it.label },
                    selectedOption = tenantViewModel.tenant.value.maritalStatus.label,
                    onOptionSelected = { selectedLabel->
                        tenantViewModel.tenant.value =
                            tenantViewModel.tenant.value.copy(
                                maritalStatus = MaritalStatus.entries.first{ it.label== selectedLabel}) },
                    isEnabled = !tenantViewModel.inputLockState.value
                )

                InputDateComp(
                    "Data de nascimento",
                    value = tenantViewModel.tenant.value.birthDate,
                    onValueChange = { tenantViewModel.changeBirthDate(it) },
                    isError = tenantViewModel.inputContainsError("birthDate"),
                    errorMessage = tenantViewModel.getInputErrorMessage("birthDate"),
                    readOnly = tenantViewModel.inputLockState.value
                )


                InputComp(
                    label = "CEP",
                    placeholder = "Ex: 01001-000",
                    value = tenantViewModel.tenant.value.address.cep,
                    onValueChange = { tenantViewModel.changeCep(it) },
                    isNumeric = true,
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.cep"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.cep")
                )

                InputComp(
                    label = "Estado",
                    placeholder = "Ex: SP",
                    value = tenantViewModel.tenant.value.address.state,
                    onValueChange = { tenantViewModel.changeState(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.state"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.state")
                )

                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = tenantViewModel.tenant.value.address.city,
                    onValueChange = { tenantViewModel.changeCity(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.city"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.city")
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = tenantViewModel.tenant.value.address.neighborhood,
                    onValueChange = { tenantViewModel.changeNeighborhood(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.neighborhood"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.neighborhood")
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = tenantViewModel.tenant.value.address.street,
                    onValueChange = { tenantViewModel.changeStreet(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.street"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.street")
                )

                InputComp(
                    label = "Número",
                    placeholder = "Ex: 68",
                    value = tenantViewModel.tenant.value.address.number,
                    onValueChange = { tenantViewModel.changeNumber(it) },
                    isNumeric = true,
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.number"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.number")
                )

                InputComp(
                    label = "Complemento",
                    placeholder = "Ex: Bloco 18",
                    value = tenantViewModel.tenant.value.address.complement,
                    onValueChange = { tenantViewModel.changeComplement(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("address.complement"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.complement")
                )

                ConfirmDialogComp(
                    tenantViewModel.showDialogState.value,
                    "Está ação deletará esta locatário",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { tenantViewModel.deleteAction() },
                    { tenantViewModel.changeShowDialog() }
                )


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (tenantViewModel.messageError.value != "") {
                        MessageErrorComp(tenantViewModel.messageError.value, 16.sp)
                    }
                    if (tenantViewModel.inputLockState.value) {
                        ButtonComp(
                            "Editar dados",
                            { Icon(Icons.Default.Edit, "editar") },
                            PrimaryColor,
                            { tenantViewModel.hiddenEditButton() },
                        )
                        ButtonComp(
                            "Excluir",
                            { Icon(Icons.Default.Delete, "deletar") },
                            CancelColor,
                            { tenantViewModel.changeShowDialog() },
                        )
                    } else {
                        if (tenantViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {

                            ButtonComp(
                                "Salvar",
                                { Icon(Icons.Default.Check, "confirmar") },
                                ConfirmColor,
                                { tenantViewModel.editAction() }
                            )

                            ButtonComp(
                                "Cancelar",
                                { Icon(Icons.Default.Cancel, "cancelar") },
                                CancelColor,
                                { tenantViewModel.hiddenEditButton() }
                            )
                        }
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