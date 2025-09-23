package com.imobly.imobly.ui.screens.tenant

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import org.jetbrains.compose.ui.tooling.preview.Preview
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.pessoa
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// Fonte padrão
val Montserrat = FontFamily.Default

data class Tenant(
    val id: String,
    val name: String,
    val property: String,
    val phone: String,
    val email: String,
    val imageResource: DrawableResource
)

@Composable
fun TenantScreen() {
    var searchText by remember { mutableStateOf("") }

    val tenants = remember {
        listOf(
            Tenant(
                id = "1",
                name = "Fernanda Moraes",
                property = "Studio Peace",
                phone = "+351 99676-001",
                email = "fernandamoraes@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "2",
                name = "Carlos Silva",
                property = "Studio Harmony",
                phone = "+351 98765-432",
                email = "carlos.silva@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "3",
                name = "Ana Santos",
                property = "Studio Premium",
                phone = "+351 91234-567",
                email = "ana.santos@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "4",
                name = "João Pereira",
                property = "Studio Comfort",
                phone = "+351 92345-678",
                email = "joao.pereira@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "5",
                name = "Mariana Costa",
                property = "Studio Elite",
                phone = "+351 93456-789",
                email = "mariana.costa@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "6",
                name = "Ricardo Almeida",
                property = "Studio Modern",
                phone = "+351 94567-890",
                email = "ricardo.almeida@gmail.com",
                imageResource = Res.drawable.pessoa
            ),
            Tenant(
                id = "7",
                name = "Patrícia Lima",
                property = "Studio Classic",
                phone = "+351 95678-901",
                email = "patricia.lima@gmail.com",
                imageResource = Res.drawable.pessoa
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
                    // Título da tela centralizado
                    Text(
                        text = "Meus Locatários",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black, // ✅ título em preto
                        fontFamily = Montserrat,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    // Linha abaixo do título (mais curta e centralizada)
                    Divider(
                        color = Color(0xFFF2603F),
                        thickness = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 30.dp) // mais espaçamento antes do botão
                    )

                    // Botão centralizado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { /* TODO: Cadastrar locatário */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF2603F)
                            ),
                            modifier = Modifier
                                .height(40.dp)
                                .width(250.dp),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Text(
                                text = "+  Cadastrar Locatário",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = Montserrat
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp)) // mais espaçamento antes do campo de busca

                    // Campo de busca
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text(
                                "Buscar um locatário",
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

                    // Lista de locatários
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(tenants) { tenant ->
                            TenantCard(tenant = tenant)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TenantCard(tenant: Tenant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Imagem do locatário
            Image(
                painter = painterResource(tenant.imageResource),
                contentDescription = "Foto de ${tenant.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = tenant.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black, // ✅ nome do locatário em preto
                        fontFamily = Montserrat
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Imóvel: ${tenant.property}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        fontFamily = Montserrat
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text("\uD83D\uDCDE", fontSize = 14.sp, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tenant.phone,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            fontFamily = Montserrat
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("\uD83D\uDCE7", fontSize = 14.sp, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tenant.email,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            fontFamily = Montserrat,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TenantScreenPreview() {
    TenantScreen()
}