package com.imobly.imobly.ui.screens.show.showreports


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.cards.ReportCardComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowReportsScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    val reports = remember {
        listOf(
            ReportData(
                title = "Parede rachando",
                description = "A parede do portão está rachando",
                date = "29/09/2025",
                status = "PENDENTE",
                message = "Vou te chamar no whatsapp para resolvermos isso",
                reporter = "Rafael Henrique Lima"
            ),
            ReportData(
                title = "Vazamento na pia",
                description = "Há um vazamento pequeno na pia da cozinha",
                date = "02/10/2025",
                status = "EM ANÁLISE",
                message = "Enviarei fotos no grupo do condomínio",
                reporter = "Fernanda Moraes"
            ),
            ReportData(
                title = "Lâmpada queimada",
                description = "Corredor do térreo sem iluminação",
                date = "10/10/2025",
                status = "RESOLVIDO",
                message = "Foi trocada pelo zelador",
                reporter = "Carlos Silva"
            )
        )
    }

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
                    TitleComp("Lista de Reportações") {
                        navController.navigate("home")
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar reportações",
                            searchText,
                            { searchText = it }
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(reports) { report ->
                            ReportCardComp(
                                report.title,
                                report.description,
                                report.date,
                                report.status,
                                report.message,
                                report.reporter
                            )
                        }
                    }
                }
            }
        }
    )
}

data class ReportData(
    val title: String,
    val description: String,
    val date: String,
    val status: String,
    val message: String,
    val reporter: String
)

@Composable
@Preview
fun ShowReportsScreenPreview() {
    val navControllerFake = rememberNavController()
    ShowReportsScreen(navControllerFake)
}