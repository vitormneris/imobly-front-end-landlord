package com.imobly.imobly.ui.screens.show.showlease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Lease
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.LeaseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowLeasesScreen(leaseViewModel: LeaseViewModel) {
    leaseViewModel.searchAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(leaseViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TitleComp(text = "Locações", buttonBackAction = { leaseViewModel.goToHome() })

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ButtonComp(
                    "Cadastrar contrato",
                    {  Icon(Icons.Default.Add, "Sinal de soma") },
                    PrimaryColor,
                    { leaseViewModel.goToCreateLease() }
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchBarComp(
                    "Buscar um imóvel",
                    leaseViewModel.searchText.value,
                    { leaseViewModel.changeSearchText(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(leaseViewModel.leases.value) { lease ->
                    LeaseCard(
                        lease = lease,
                        leaseViewModel = leaseViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun LeaseCard(lease: Lease, leaseViewModel: LeaseViewModel) {
    Card(
        modifier = Modifier
            .widthIn(max = 1000.dp)
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(lease.property.title, style = MaterialTheme.typography.titleMedium)
            Text("Locatário: ${lease.tenant.firstName}")
            Text("Período: ${lease.startDate} - ${lease.endDate}")
            Text("Valor: ${lease.monthlyRent}")
            Text(
                text = "Status: ${if (lease.isEnabled) "Ativa" else "Inativa"}",
                color = if (lease.isEnabled) PrimaryColor else CancelColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonComp(
                    text = "Editar",
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Editar") },
                    color = PrimaryColor,
                    action = { leaseViewModel.goToEditLease(lease)  }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewShowLeaseScreen() {
    val navControllerFake = rememberNavController()
    ShowLeasesScreen(LeaseViewModel(navControllerFake))
}
