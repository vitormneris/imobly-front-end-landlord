package com.imobly.imobly.ui.screens.edit.editproperty

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
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.carousel.CarouselComp
import com.imobly.imobly.ui.components.carousel.CarouselKamelComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDropdownComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.PropertyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditPropertyScreen(propertyViewModel: PropertyViewModel) {
    val scrollState = rememberScrollState()

    propertyViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost( propertyViewModel.snackMessage.value ) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComp(
                "Dados do imóvel",
                { propertyViewModel.goToShowProperties() }
            )

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (propertyViewModel.selectedImages.value.isEmpty()) {
                    CarouselKamelComp( propertyViewModel.property )
                } else {
                    CarouselComp( propertyViewModel.selectedImages )
                }

                if (!propertyViewModel.inputLockState.value) {
                    ImagePickerComp("Escolha as imagens", propertyViewModel.selectedImages)
                }

                InputComp(
                    label = "Título",
                    placeholder = "Ex: Predio em copacabana",
                    value = propertyViewModel.property.value.title,
                    onValueChange = { propertyViewModel.changeTitle(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("title"),
                    errorMessage = propertyViewModel.getInputErrorMessage("title")
                )

                InputComp(
                    label = "Aluguel mensal",
                    placeholder = "Ex: 1.800,00",
                    value = propertyViewModel.property.value.monthlyRent,
                    onValueChange = { propertyViewModel.changeMonthlyRent(it) },
                    isNumeric = true,
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("monthlyRent"),
                    errorMessage = propertyViewModel.getInputErrorMessage("monthlyRent")
                )

                InputDropdownComp(
                    label = "Categoria",
                    options = propertyViewModel.categoriesOptions(),
                    selectedOption = propertyViewModel.property.value.category.title,
                    onOptionSelected = { selectedOption ->
                        val category = propertyViewModel.categories.value.first { it.id == selectedOption }
                        propertyViewModel.changeCategory(category)
                    },
                    isEnabled = !propertyViewModel.inputLockState.value

                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputComp(
                        label = "Nº Domitórios",
                        placeholder = "Ex: 4",
                        value = propertyViewModel.property.value.bedrooms,
                        onValueChange = { propertyViewModel.changeBedrooms(it) },
                        isNumeric = true,
                        readOnly = propertyViewModel.inputLockState.value,
                        isError = propertyViewModel.inputContainsError("bedrooms"),
                        errorMessage = propertyViewModel.getInputErrorMessage("bedrooms"),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Área (m²)",
                        placeholder = "Ex: 333.25",
                        value = propertyViewModel.property.value.area,
                        onValueChange = { propertyViewModel.changeArea(it) },
                        isNumeric = true,
                        readOnly = propertyViewModel.inputLockState.value,
                        isError = propertyViewModel.inputContainsError("area"),
                        errorMessage = propertyViewModel.getInputErrorMessage("area"),
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
                        label = "Nº Banheiros",
                        placeholder = "Ex: 2",
                        value = propertyViewModel.property.value.bathrooms,
                        onValueChange = { propertyViewModel.changeBathrooms(it) },
                        isNumeric = true,
                        readOnly = propertyViewModel.inputLockState.value,
                        isError = propertyViewModel.inputContainsError("bathrooms"),
                        errorMessage = propertyViewModel.getInputErrorMessage("bathrooms"),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Vagas gara.",
                        placeholder = "Ex: 3",
                        value = propertyViewModel.property.value.garageSpaces,
                        onValueChange = { propertyViewModel.changeGarageSpaces(it) },
                        isNumeric = true,
                        readOnly = propertyViewModel.inputLockState.value,
                        isError = propertyViewModel.inputContainsError("garageSpaces"),
                        errorMessage = propertyViewModel.getInputErrorMessage("garageSpaces"),
                        modifier = Modifier.weight(1f)
                    )
                }

                InputComp(
                    label = "Descrição",
                    placeholder = "Ex: Este é um maravilhoso apartamento",
                    value = propertyViewModel.property.value.description,
                    onValueChange = { propertyViewModel.changeDescription(it) },
                    singleLine = false,
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("description"),
                    errorMessage = propertyViewModel.getInputErrorMessage("description")
                )

                InputComp(
                    label = "CEP",
                    placeholder = "Ex: 01001-000",
                    value = propertyViewModel.property.value.address.cep,
                    onValueChange = { propertyViewModel.changeCep(it) },
                    isNumeric = true,
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.cep"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.cep")
                )

                InputComp(
                    label = "Estado",
                    placeholder = "Ex: SP",
                    value = propertyViewModel.property.value.address.state,
                    onValueChange = { propertyViewModel.changeState(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.state"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.state")
                )

                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = propertyViewModel.property.value.address.city,
                    onValueChange = { propertyViewModel.changeCity(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.city"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.city")
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = propertyViewModel.property.value.address.neighborhood,
                    onValueChange = { propertyViewModel.changeNeighborhood(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.neighborhood"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.neighborhood")
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = propertyViewModel.property.value.address.street,
                    onValueChange = { propertyViewModel.changeStreet(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.street"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.street")
                )

                InputComp(
                    label = "Número",
                    placeholder = "Ex: 68",
                    value = propertyViewModel.property.value.address.number,
                    onValueChange = { propertyViewModel.changeNumber(it) },
                    isNumeric = true,
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.number"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.number")
                )

                InputComp(
                    label = "Complemento",
                    placeholder = "Ex: Bloco 18",
                    value = propertyViewModel.property.value.address.complement,
                    onValueChange = { propertyViewModel.changeComplement(it) },
                    readOnly = propertyViewModel.inputLockState.value,
                    isError = propertyViewModel.inputContainsError("address.complement"),
                    errorMessage = propertyViewModel.getInputErrorMessage("address.complement")
                )

                ConfirmDialogComp(
                    propertyViewModel.showDialogState.value,
                    "Está ação deletará esta propriedade",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { propertyViewModel.deleteAction() },
                    { propertyViewModel.changeShowDialog() }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (propertyViewModel.messageError.value != "") {
                        MessageErrorComp(propertyViewModel.messageError.value, 14.sp)
                    }

                    if (propertyViewModel.inputLockState.value) {
                        FlowRow {

                            ButtonComp(
                                "Editar dados",
                                { Icon(Icons.Default.Edit, "editar") },
                                PrimaryColor,
                                { propertyViewModel.hiddenEditButton() },
                                185.dp,
                                16.sp
                            )

                            ButtonComp(
                                "Excluir",
                                { Icon(Icons.Default.Delete, "deletar") },
                                CancelColor,
                                { propertyViewModel.changeShowDialog() },
                                140.dp,
                                16.sp
                            )
                        }

                    } else {
                        if (propertyViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {
                            FlowRow {
                                ButtonComp(
                                    "Salvar",
                                    { Icon(Icons.Default.Check, "confirmar") },
                                    ConfirmColor,
                                    { propertyViewModel.editAction() },
                                    140.dp,
                                    16.sp
                                )

                                ButtonComp(
                                    "Cancelar",
                                    { Icon(Icons.Default.Cancel, "cancelar") },
                                    CancelColor,
                                    { propertyViewModel.hiddenEditButton() },
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

@Composable
@Preview
fun EditPropertyScreenPreview() {
    val navControllerFake = rememberNavController()
    EditPropertyScreen(PropertyViewModel(navControllerFake))
}