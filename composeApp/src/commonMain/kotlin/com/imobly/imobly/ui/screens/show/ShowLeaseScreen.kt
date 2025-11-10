package com.imobly.imobly.ui.screens.show

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowLeaseScreen(
    onBack: () -> Unit = {},
    onEdit: (String) -> Unit = {}
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var selectedLease by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }


    val leases = listOf(
        Lease("Apartamento 101", "João Silva", "01/01/2025", "31/12/2025", "R$ 1.200,00", true),
        Lease("Casa 202", "Maria Oliveira", "15/02/2025", "14/02/2026", "R$ 2.000,00", false),
        Lease("Cobertura 303", "Carlos Santos", "10/03/2025", "09/03/2026", "R$ 3.500,00", true)
    )

    val filteredLeases = leases.filter {
        it.property.contains(searchQuery, ignoreCase = true) ||
                it.tenant.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = { TopBarComp() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TitleComp(text = "Locações", buttonBackAction = onBack)

            Spacer(modifier = Modifier.height(8.dp))

            SearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredLeases) { lease ->
                    LeaseCard(
                        lease = lease,
                        onEditClick = { onEdit(lease.property) },
                        onStatusToggle = {
                            selectedLease = lease.property
                            showConfirmDialog = true
                        }
                    )
                }
            }
        }
    }

    ConfirmDialogComp(
        showDialog = showConfirmDialog,
        title = "Alterar status da locação",
        message = "Tem certeza que deseja alterar o status de ${selectedLease ?: "esta locação"}?",
        onConfirm = { showConfirmDialog = false },
        onDismiss = { showConfirmDialog = false }
    )
}

data class Lease(
    val property: String,
    val tenant: String,
    val startDate: String,
    val endDate: String,
    val value: String,
    val isActive: Boolean
)

@Composable
fun LeaseCard(
    lease: Lease,
    onEditClick: () -> Unit,
    onStatusToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(lease.property, style = MaterialTheme.typography.titleMedium)
            Text("Locatário: ${lease.tenant}")
            Text("Período: ${lease.startDate} - ${lease.endDate}")
            Text("Valor: ${lease.value}")
            Text(
                text = "Status: ${if (lease.isActive) "Ativa" else "Inativa"}",
                color = if (lease.isActive) PrimaryColor else CancelColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonComp(
                    text = if (lease.isActive) "Desativar" else "Ativar",
                    icon = {},
                    color = if (lease.isActive) CancelColor else PrimaryColor
                ) {
                    onStatusToggle()
                }

                ButtonComp(
                    text = "Editar",
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Editar") },
                    color = PrimaryColor
                ) {
                    onEditClick()
                }
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text("Buscar locação...", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowLeaseScreen() {
    ShowLeaseScreen(onBack = {}, onEdit = {})
}
