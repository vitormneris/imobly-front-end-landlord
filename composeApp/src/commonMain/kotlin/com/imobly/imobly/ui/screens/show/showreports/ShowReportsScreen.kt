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
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Report
import com.imobly.imobly.ui.components.cards.ReportCardComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.viewmodel.ReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowReportsScreen(reportViewModel: ReportViewModel) {

    val reports: MutableState<List<Report>> = remember{ reportViewModel.reports}
    reportViewModel.findAllAction()

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
                    TitleComp("Lista de Reportações", {
                        reportViewModel.goToHome()
                    })

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar reportações",
                            reportViewModel.searchText.value,
                            { reportViewModel.changeSearchText(it) }
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
                        items(reports.value) { report ->
                            ReportCardComp(
                                report.title,
                                report.message,
                                report.moment.toString(),
                                report.status.description,
                                report.response,
                                report.tenant.firstName,
                                {
                                    reportViewModel.goToEditReport(report)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun ShowReportsScreenPreview() {
    val navControllerFake = rememberNavController()
    ShowReportsScreen(ReportViewModel(navControllerFake))
}