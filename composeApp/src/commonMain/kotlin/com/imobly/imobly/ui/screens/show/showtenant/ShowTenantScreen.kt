package com.imobly.imobly.ui.screens.show.showtenant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Tenant
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.TenantViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ShowTenantScreen(tenantViewModel: TenantViewModel) {

    val tenants: MutableState<List<Tenant>> = remember { tenantViewModel.tenants}
    tenantViewModel.findAllAction()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = {
            SnackbarHost(tenantViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(BackGroundColor)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TitleComp("Meus Locatários", {
                        tenantViewModel.goToHome()
                    })

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar locatários por nome ou CPF",
                            tenantViewModel.searchText.value,
                            { tenantViewModel.changeSearchText(it) },
                            { tenantViewModel.searchAction() }
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(tenants.value) { tenant ->
                            TenantCard(tenant, tenantViewModel)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TenantCard(tenant: Tenant, tenantViewModel: TenantViewModel) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            KamelImage(
                resource = { asyncPainterResource(tenant.pathImage) },
                contentDescription = "Imagem do locatário ${tenant.firstName}",
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(max = 200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator({ progress }) },
                onFailure = { CircularProgressIndicator() }
            )

            Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "${tenant.firstName}  ${tenant.lastName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = montserratFont()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text("\uD83D\uDCDE", fontSize = 14.sp, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tenant.telephones.telephone1,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            fontFamily = montserratFont()
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
                            fontFamily = montserratFont(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Button(
                onClick = {
                    tenantViewModel.goToEditTenant(tenant)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
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

@Preview
@Composable
fun ShowTenantScreenPreview() {
    val navControllerFake = rememberNavController()
    ShowTenantScreen(TenantViewModel(navControllerFake))
}