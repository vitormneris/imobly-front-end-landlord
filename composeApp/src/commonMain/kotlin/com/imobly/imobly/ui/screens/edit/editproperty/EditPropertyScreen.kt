package com.imobly.imobly.ui.screens.edit.editproperty

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.carousel.CarouselComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.IconColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.icons.EditSquareIcon
import com.imobly.imobly.viewmodel.EditPropertyViewModel
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.image_house_1
import imobly.composeapp.generated.resources.image_house_2
import imobly.composeapp.generated.resources.image_house_3
import imobly.composeapp.generated.resources.image_house_4
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditPropertyScreen(navController: NavHostController) {
    val modelView = EditPropertyViewModel()
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
            TitleComp("Dados do imóvel", { modelView.topBarAction(navController) })

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CarouselComp(
                    modelView.selectedImages
                )

                ImagePickerComp("Escolha a imagem", modelView.selectedImages)

                InputComp(
                    label = "Título",
                    placeholder = "Ex: Predio em copacabana",
                    value = modelView.property.value.title,
                    onValueChange = { modelView.changeTitle(it) }
                )

                InputComp(
                    label = "Aluguel mensal",
                    placeholder = "Ex: 1.800,00",
                    value = modelView.property.value.rentValue,
                    onValueChange = { modelView.changeRental(it) },
                    isNumeric = true
                )

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "Nº Domitórios",
                        placeholder = "Ex: 4",
                        value = modelView.property.value.bedrooms,
                        onValueChange = { modelView.changeBedrooms(it) },
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Área (m²)",
                        placeholder = "Ex: 333.25",
                        value = modelView.property.value.area,
                        onValueChange = { modelView.changeArea(it) },
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
                        value = modelView.property.value.bathrooms,
                        onValueChange = { modelView.changeBathrooms(it) },
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp

                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Vagas gara.",
                        placeholder = "Ex: 3",
                        value = modelView.property.value.garageSpaces,
                        onValueChange = { modelView.changeGarageSpaces(it) },
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp
                    )
                }


                InputComp(
                    label = "Descrição",
                    placeholder = "Ex: Este é um maravilhoso apartamento",
                    value = modelView.property.value.description,
                    onValueChange = { modelView.changeDescription(it) },
                    singleLine = false
                )

                InputComp(
                    label = "CEP",
                    placeholder = "Ex: 01001-000",
                    value = modelView.property.value.address.cep,
                    onValueChange = { modelView.changeCep(it) },
                    isNumeric = true
                )

                InputComp(
                    label = "Estado",
                    placeholder = "Ex: SP",
                    value = modelView.property.value.address.state,
                    onValueChange = { modelView.changeState(it) }
                )

                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = modelView.property.value.address.city,
                    onValueChange = { modelView.changeCity(it) }
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = modelView.property.value.address.neighborhood,
                    onValueChange = { modelView.changeNeighborhood(it) }
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = modelView.property.value.address.street,
                    onValueChange = { modelView.changeStreet(it) }
                )

                InputComp(
                    label = "Número",
                    placeholder = "Ex: 68",
                    value = modelView.property.value.address.number,
                    onValueChange = { modelView.changeNumber(it) },
                    isNumeric = true
                )

                InputComp(
                    label = "Complemento",
                    placeholder = "Ex: Bloco 18",
                    value = modelView.property.value.address.complement,
                    onValueChange = { modelView.changeComplement(it) }
                )

                Box(Modifier.align(Alignment.CenterHorizontally)) {
                    ButtonComp(
                        "Editar dados",
                        { EditSquareIcon(32.dp, "Edit square", IconColor) },
                        PrimaryColor,
                        { modelView.editAction() }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun EditPropertyScreenPreview() {
    val navController = rememberNavController()
    EditPropertyScreen(navController)
}