package com.imobly.imobly.ui.screens.show.showproperty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Property
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PropertyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPropertiesScreen(propertyViewModel: PropertyViewModel) {

    val properties: MutableState<List<Property>> = remember { propertyViewModel.properties }
    propertyViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    }


                    TitleComp("Meus Imóveis", {
                        propertyViewModel.goToHome()
                    })


                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonComp(
                            "Cadastrar imóvel",
                            {  Icon(Icons.Default.Add, "Sinal de soma") },
                            PrimaryColor,
                            { propertyViewModel.goToCreateProperty() }
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar um imóvel",
                            propertyViewModel.searchText.value,
                            { propertyViewModel.changeSearchText(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(properties.value) { property ->
                            PropertyCardComp(property, propertyViewModel)
                        }
                    }

                }
            }
        }
    )
}

@Composable
fun PropertyCardComp(property: Property, propertyViewModel: PropertyViewModel) {
    Card(
        modifier = Modifier
            .widthIn(max = 1000.dp)
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
//                Image(
//                    painter = painterResource(property.pathImages as DrawableResource),
//                    contentDescription = "Imagem do imóvel ${property.title}",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
//                    contentScale = ContentScale.Crop
//                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            color = Color(0xFFF2603F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "R$ ${property.rentalValue}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = montserratFont()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = property.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF2D5B7A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = montserratFont()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${property.address.state}/${property.address.city}, ${property.address.street}, ${property.address.number}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = montserratFont()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = Color(0xFFF2603F).copy(alpha = 0.2f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Check, "check")
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Área: ${property.area} m²",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color(0xFF333333),
                                fontFamily = montserratFont()
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = Color(0xFFF2603F).copy(alpha = 0.2f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Check, "check")
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Nª quartos: ${property.bedrooms}",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color(0xFF333333),
                                fontFamily = montserratFont()
                            )
                        }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = Color(0xFFF2603F).copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Check, "check")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Vagas garagem: ${property.garageSpaces}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            fontFamily = montserratFont()
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = Color(0xFFF2603F).copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Check, "check")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Nª banheiros: ${property.bathrooms}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            fontFamily = montserratFont()
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        propertyViewModel.goToEditProperty(property)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF2603F)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Ver Detalhes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = montserratFont()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowPropertiesScreenPreview() {
    val navControllerFake = rememberNavController()
    ShowPropertiesScreen(PropertyViewModel(navControllerFake))
}