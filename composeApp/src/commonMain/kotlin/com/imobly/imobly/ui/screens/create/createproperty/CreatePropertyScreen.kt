package com.imobly.imobly.ui.screens.create.createproperty

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.ReportStatus
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.carousel.CarouselComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.PropertyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreatePropertyScreen(propertyViewModel: PropertyViewModel) {
    val scrollState = rememberScrollState()

    propertyViewModel.whenStartingThePage()
    propertyViewModel.resetPage()

    MaterialTheme {

        Scaffold(
            topBar = {
                TopBarComp()
            },
            snackbarHost = {
                SnackbarHost(propertyViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TitleComp("Cadastrar imóvel", {
                    propertyViewModel.goToShowProperties()
                })

                Column(
                    Modifier
                        .verticalScroll(scrollState)
                        .widthIn(max = 1000.dp)
                        .fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CarouselComp(
                        propertyViewModel.selectedImages
                    )

                    ImagePickerComp("Escolha as imagens", propertyViewModel.selectedImages)

                    DropdownComp(
                        label = "Categoria",
                        placeholder = "",
                        options = propertyViewModel.categories.value.map { it.title },
                        selectedOption = propertyViewModel.property.value.category!!.title,
                        onOptionSelected = { selectedOption ->
                            val category = propertyViewModel.categories.value.first { it.title == selectedOption }
                            propertyViewModel.changeCategory(category)
                        },
                    )
                    
                    InputComp(
                        label = "Título",
                        placeholder = "Ex: Predio em copacabana",
                        value = propertyViewModel.property.value.title,
                        onValueChange = { propertyViewModel.changeTitle(it) },
                        isError = propertyViewModel.inputContainsError("title"),
                        errorMessage = propertyViewModel.getInputErrorMessage("title")
                    )

                    InputComp(
                        label = "Aluguel mensal",
                        placeholder = "Ex: 1.800,00",
                        value = propertyViewModel.property.value.monthlyRent,
                        onValueChange = { propertyViewModel.changeMonthlyRent(it) },
                        isNumeric = true,
                        isError = propertyViewModel.inputContainsError("monthlyRent"),
                        errorMessage = propertyViewModel.getInputErrorMessage("monthlyRent")
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
                            isError = propertyViewModel.inputContainsError("bathrooms"),
                            errorMessage = propertyViewModel.getInputErrorMessage("bathrooms"),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.size(10.dp))
                        InputComp(
                            label = "Vagas garagem",
                            placeholder = "Ex: 3",
                            value = propertyViewModel.property.value.garageSpaces,
                            onValueChange = { propertyViewModel.changeGarageSpaces(it) },
                            isNumeric = true,
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
                            isError = propertyViewModel.inputContainsError("description"),
                            errorMessage = propertyViewModel.getInputErrorMessage("description")
                    )

                    InputComp(
                        label = "CEP",
                        placeholder = "Ex: 01001-000",
                        value = propertyViewModel.property.value.address.cep,
                        onValueChange = { propertyViewModel.changeCep(it) },
                        isNumeric = true,
                        isError = propertyViewModel.inputContainsError("address.cep"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.cep")
                    )

                    InputComp(
                        label = "Estado",
                        placeholder = "Ex: SP",
                        value = propertyViewModel.property.value.address.state,
                        onValueChange = { propertyViewModel.changeState(it) },
                        isError = propertyViewModel.inputContainsError("address.state"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.state")
                    )

                    InputComp(
                        label = "Cidade",
                        placeholder = "Ex: Guarulhos",
                        value = propertyViewModel.property.value.address.city,
                        onValueChange = { propertyViewModel.changeCity(it) },
                        isError = propertyViewModel.inputContainsError("address.city"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.city")
                    )

                    InputComp(
                        label = "Bairro",
                        placeholder = "Ex: Campo verde",
                        value = propertyViewModel.property.value.address.neighborhood,
                        onValueChange = { propertyViewModel.changeNeighborhood(it) },
                        isError = propertyViewModel.inputContainsError("address.neighborhood"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.neighborhood")
                    )

                    InputComp(
                        label = "Rua",
                        placeholder = "Ex: Olinda Alves",
                        value = propertyViewModel.property.value.address.street,
                        onValueChange = { propertyViewModel.changeStreet(it) },
                        isError = propertyViewModel.inputContainsError("address.street"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.street")
                    )

                    InputComp(
                        label = "Número",
                        placeholder = "Ex: 68",
                        value = propertyViewModel.property.value.address.number,
                        onValueChange = { propertyViewModel.changeNumber(it) },
                        isNumeric = true,
                        isError = propertyViewModel.inputContainsError("address.number"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.number")
                    )

                    InputComp(
                        label = "Complemento",
                        placeholder = "Ex: Bloco 18",
                        value = propertyViewModel.property.value.address.complement,
                        onValueChange = { propertyViewModel.changeComplement(it) },
                        isError = propertyViewModel.inputContainsError("address.complement"),
                        errorMessage = propertyViewModel.getInputErrorMessage("address.complement")
                    )


                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (propertyViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {
                            if (propertyViewModel.messageError.value != "") {
                                MessageErrorComp(propertyViewModel.messageError.value, 14.sp)
                            }
                            Box(Modifier.align(Alignment.CenterHorizontally)) {
                                ButtonComp(
                                    "Cadastrar Imóvel",
                                    { Icon(Icons.Default.Create, "check") },
                                    PrimaryColor,
                                    { propertyViewModel.createAction() }
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
fun CreatePropertyScreen() {
    val navControllerFake = rememberNavController()
    CreatePropertyScreen(PropertyViewModel(navControllerFake))
}
