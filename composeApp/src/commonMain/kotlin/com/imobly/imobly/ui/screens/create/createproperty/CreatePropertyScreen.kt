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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.carousel.CarouselComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.PropertyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreatePropertyScreen(propertyViewModel: PropertyViewModel) {
    val scrollState = rememberScrollState()

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
            TitleComp("Cadastrar imóvel", {
                propertyViewModel.goToShowProperties()
            })

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CarouselComp(
                    propertyViewModel.selectedImages
                )

                ImagePickerComp("Escolha as imagens", propertyViewModel.selectedImages)

                InputComp(
                    label = "Título",
                    placeholder = "Ex: Predio em copacabana",
                    value = propertyViewModel.property.value.title,
                    onValueChange = { propertyViewModel.changeTitle(it) }
                )

                InputComp(
                    label = "Aluguel mensal",
                    placeholder = "Ex: 1.800,00",
                    value = propertyViewModel.property.value.rentalValue,
                    onValueChange = { propertyViewModel.changeRental(it) },
                    isNumeric = true
                )

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "Nº Domitórios",
                        placeholder = "Ex: 4",
                        value = propertyViewModel.property.value.bedrooms,
                        onValueChange = { propertyViewModel.changeBedrooms(it) },
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Área (m²)",
                        placeholder = "Ex: 333.25",
                        value = propertyViewModel.property.value.area,
                        onValueChange = { propertyViewModel.changeArea(it) },
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp
                    )
                }

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "Nº Banheiros",
                        placeholder = "Ex: 2",
                        value = propertyViewModel.property.value.bathrooms,
                        onValueChange = { propertyViewModel.changeBathrooms(it) },
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp

                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Vagas gara.",
                        placeholder = "Ex: 3",
                        value = propertyViewModel.property.value.garageSpaces,
                        onValueChange = { propertyViewModel.changeGarageSpaces(it) },
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp
                    )
                }


                InputComp(
                    label = "Descrição",
                    placeholder = "Ex: Este é um maravilhoso apartamento",
                    value = propertyViewModel.property.value.description,
                    onValueChange = { propertyViewModel.changeDescription(it) },
                    singleLine = false
                )

                InputComp(
                    label = "CEP",
                    placeholder = "Ex: 01001-000",
                    value = propertyViewModel.property.value.address.cep,
                    onValueChange = { propertyViewModel.changeCep(it) },
                    isNumeric = true
                )

                InputComp(
                    label = "Estado",
                    placeholder = "Ex: SP",
                    value = propertyViewModel.property.value.address.state,
                    onValueChange = { propertyViewModel.changeState(it) }
                )

                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = propertyViewModel.property.value.address.city,
                    onValueChange = { propertyViewModel.changeCity(it) }
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = propertyViewModel.property.value.address.neighborhood,
                    onValueChange = { propertyViewModel.changeNeighborhood(it) }
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = propertyViewModel.property.value.address.street,
                    onValueChange = { propertyViewModel.changeStreet(it) }
                )

                InputComp(
                    label = "Número",
                    placeholder = "Ex: 68",
                    value = propertyViewModel.property.value.address.number,
                    onValueChange = { propertyViewModel.changeNumber(it) },
                    isNumeric = true
                )

                InputComp(
                    label = "Complemento",
                    placeholder = "Ex: Bloco 18",
                    value = propertyViewModel.property.value.address.complement,
                    onValueChange = { propertyViewModel.changeComplement(it) }
                )


                Box(Modifier.align(Alignment.CenterHorizontally)) {
                    ButtonComp(
                        "Confirmar Imóvel",
                        { Icon(Icons.Default.Create, "check") },
                        PrimaryColor,
                        { propertyViewModel.createAction() }
                    )
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
