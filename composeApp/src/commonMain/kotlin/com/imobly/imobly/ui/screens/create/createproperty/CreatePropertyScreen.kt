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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.model.Property
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.carousel.CarouselComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.IconColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.icons.CheckIcon
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreatePropertyScreen(navController: NavHostController) {
    val property = remember { mutableStateOf(Property()) }
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
                navController.navigate("home")
            })

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CarouselComp(
                    mutableStateOf(emptyList())
                )

                InputComp(
                    label = "Título",
                    placeholder = "Ex: Predio em copacabana",
                    value = property.value.title,
                    onValueChange = { property.value = property.value.copy(title = it) }
                )

                InputComp(
                    label = "Aluguel mensal",
                    placeholder = "Ex: 1.800,00",
                    value = property.value.rentValue,
                    onValueChange = { property.value = property.value.copy(
                        rentValue = if (it.toDoubleOrNull() != null || it == "") {
                            it
                        } else {
                            property.value.rentValue
                        }
                    )},
                    isNumeric = true
                )

                Row(
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputComp(
                        label = "Nº Domitórios",
                        placeholder = "Ex: 4",
                        value = property.value.bedrooms,
                        onValueChange = { property.value = property.value.copy(
                            bedrooms = if (it.toIntOrNull() != null || it == "") {
                                it
                            } else {
                                property.value.bedrooms
                            }
                        )},
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp
                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Área (m²)",
                        placeholder = "Ex: 333.25",
                        value = property.value.area,
                        onValueChange = { property.value = property.value.copy(
                            area = if (it.toDoubleOrNull() != null || it == "") {
                                it
                            } else {
                                property.value.area
                            }
                        )},
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
                        value = property.value.bathrooms,
                        onValueChange = { property.value = property.value.copy(
                            bathrooms = if (it.toIntOrNull() != null || it == "") {
                                it
                            } else {
                                property.value.bathrooms
                            }
                        )},
                        isNumeric = true,
                        fractionWidth = 0.4f,
                        maxWidth = 780.dp

                    )
                    Spacer(Modifier.size(10.dp))
                    InputComp(
                        label = "Vagas gara.",
                        placeholder = "Ex: 3",
                        value = property.value.garageSpaces,
                        onValueChange = { property.value = property.value.copy(
                            garageSpaces = if (it.toIntOrNull() != null || it == "") {
                                it
                            } else {
                                property.value.garageSpaces
                            }
                        )},
                        isNumeric = true,
                        fractionWidth = 0.6f,
                        maxWidth = 780.dp
                    )
                }


                InputComp(
                    label = "Descrição",
                    placeholder = "Ex: Este é um maravilhoso apartamento",
                    value = property.value.description,
                    onValueChange = { property.value = property.value.copy(description = it) },
                    singleLine = false
                )

                InputComp(
                    label = "CEP",
                    placeholder = "Ex: 01001-000",
                    value = property.value.address.cep,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(cep = it)
                        )
                    },
                    isNumeric = true
                )

                InputComp(
                    label = "Estado",
                    placeholder = "Ex: SP",
                    value = property.value.address.state,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(state = it)
                        )
                    }
                )

                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = property.value.address.city,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(city = it)
                        )
                    }
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = property.value.address.neighborhood,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(neighborhood = it)
                        )
                    }
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = property.value.address.street,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(street = it)
                        )
                    }
                )

                InputComp(
                    label = "Número",
                    placeholder = "Ex: 68",
                    value = property.value.address.number,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(
                                number = if (it.toIntOrNull() != null || it == "") {
                                    it
                                } else {
                                    property.value.address.number
                                }
                            )
                        )
                    },
                    isNumeric = true
                )

                InputComp(
                    label = "Complemento",
                    placeholder = "Ex: Bloco 18",
                    value = property.value.address.complement,
                    onValueChange = {
                        property.value = property.value.copy(
                            address = property.value.address.copy(complement = it)
                        )
                    }
                )

                Box(Modifier.align(Alignment.CenterHorizontally)) {
                    ButtonComp(
                        "Confirmar Imóvel",
                        { CheckIcon(32.dp, "Check", IconColor) },
                        PrimaryColor,
                        {}
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CreatePropertyScreen() {
    val navController = rememberNavController()
    CreatePropertyScreen(navController)
}
