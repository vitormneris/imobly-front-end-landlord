package com.imobly.imobly.ui.screens.properties

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import org.jetbrains.compose.ui.tooling.preview.Preview
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.imobly
import imobly.composeapp.generated.resources.lugar
import org.jetbrains.compose.resources.painterResource

// Definindo a fonte Montserrat - versão corrigida
val Montserrat = FontFamily.Default

data class Property(
    val id: String,
    val title: String,
    val address: String,
    val features: List<String>,
    val price: String,
    val imageResource: Any
)

@Composable
fun PropertiesScreen() {
    var searchText by remember { mutableStateOf("") }

    val properties = remember {
        listOf(
            Property(
                id = "1",
                title = "Studio Peace",
                address = "Rua Imaginária, 250 - Jacínio Cúcia",
                features = listOf("2 dormitórios", "1 banheiro", "área de lazer com churrasqueira"),
                price = "R$ 1.200/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "2",
                title = "Studio Harmony",
                address = "Avenida dos Sonhos, 123 - Centro",
                features = listOf("3 dormitórios", "2 banheiros", "varanda gourmet"),
                price = "R$ 1.800/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "3",
                title = "Studio Premium",
                address = "Praça das Flores, 456 - Jardim Paulista",
                features = listOf("1 dormitório", "1 banheiro", "piscina compartilhada"),
                price = "R$ 950/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "4",
                title = "Studio Comfort",
                address = "Alameda das Árvores, 789 - Vila Nova",
                features = listOf("2 dormitórios", "1 banheiro", "garagem"),
                price = "R$ 1.100/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "5",
                title = "Studio Elite",
                address = "Rua das Pedras, 321 - Centro Histórico",
                features = listOf("3 dormitórios", "2 banheiros", "sacada"),
                price = "R$ 2.000/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "6",
                title = "Studio Modern",
                address = "Avenida Contemporânea, 654 - Novo Bairro",
                features = listOf("2 dormitórios", "2 banheiros", "varanda"),
                price = "R$ 1.500/mês",
                imageResource = Res.drawable.lugar
            ),
            Property(
                id = "7",
                title = "Studio Classic",
                address = "Travessa Antiga, 987 - Centro Histórico",
                features = listOf("1 dormitório", "1 banheiro", "vista para o mar"),
                price = "R$ 1.300/mês",
                imageResource = Res.drawable.lugar
            )
        )
    }

    Scaffold(
        topBar = {
            TopBarComp()
        },
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
                        .padding(24.dp)
                ) {
                    // Header com logo Imobly
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    }

                    // Botão Cadastrar Imóvel - CENTRALIZADO e TAMANHO PERSONALIZADO
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { /* Cadastrar imóvel */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF2603F)
                            ),
                            modifier = Modifier
                                .height(40.dp) // Altura personalizada
                                .width(200.dp), // Largura personalizada
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Text(
                                text = "+  Cadastrar Imóvel",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = Montserrat
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de busca com emoji de lupa
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text(
                                "Buscar um imóvel",
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontFamily = Montserrat
                            )
                        },
                        trailingIcon = {
                            Text(
                                text = "🔍",
                                modifier = Modifier.size(20.dp),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFFFFFF),
                            unfocusedContainerColor = Color(0xFFFFFFFF),
                            focusedIndicatorColor = Color(0xFFF2603F),
                            unfocusedIndicatorColor = Color(0xFFE0E0E0)
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Lista de imóveis com scroll
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(properties) { property ->
                            PropertyCardPremium(property = property)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PropertyCardPremium(property: Property) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Imagem do imóvel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(property.imageResource as org.jetbrains.compose.resources.DrawableResource),
                    contentDescription = "Imagem do imóvel ${property.title}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Preço flutuante
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
                        text = property.price,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = Montserrat
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
                    fontFamily = Montserrat
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = property.address,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = Montserrat
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    property.features.forEach { feature ->
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
                                Text(
                                    text = "✓",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFFF2603F)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = feature,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color(0xFF333333),
                                fontFamily = Montserrat
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão Ver Detalhes - TAMANHO PERSONALIZADO
                Button(
                    onClick = { /* Ver detalhes */ },
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
                        fontFamily = Montserrat
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PropertiesScreenPreview() {
    PropertiesScreen()
}