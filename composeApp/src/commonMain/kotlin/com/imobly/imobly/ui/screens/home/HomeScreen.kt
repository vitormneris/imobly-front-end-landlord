package com.imobly.imobly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.charts.BarPlotComp
import com.imobly.imobly.ui.components.charts.PieChartComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val scrollState = rememberScrollState()
    val horizantalScrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopBarComp()
        },
       snackbarHost = { SnackbarHost( homeViewModel.snackMessage.value ) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(BackGroundColor)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {

                val adaptiveInfo = currentWindowAdaptiveInfo()
                val adaptiveWidth = when {
                    adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> 1100.dp
                    adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> 700.dp
                    else -> 600.dp
                }


                val months =
                    listOf(
                        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
                    )


                val totalByMonth = listOf(
                    37920.00f,  // Janeiro - 13 imóveis
                    41380.00f,  // Fevereiro - 14 imóveis (um a mais)
                    35840.00f,  // Março - 12 imóveis (um a menos)
                    39270.00f,  // Abril - 13 imóveis
                    40050.00f,  // Maio - 13 imóveis
                    42110.00f,  // Junho - 14 imóveis (um a mais)
                    37480.00f,  // Julho - 12 imóveis (um a menos)
                    40630.00f,  // Agosto - 13 imóveis
                    39750.00f,  // Setembro - 13 imóveis
                    41900.00f,  // Outubro - 14 imóveis (um a mais)
                    38940.00f,  // Novembro - 13 imóveis
                    38010.00f   // Dezembro - 12 imóveis (um a menos)
                )


                data class ChartData(
                    val x: List<String>,
                    val y: List<Float>
                )

                val paymentData = ChartData(
                    x = listOf("PAID", "NOT_PAID"),
                    y = listOf(11.0f, 3.0f)
                )
                val rentData = ChartData(
                    x = listOf("RENTED", "NOT_RENTED"),
                    y = listOf(13.0f, 1.0f)
                )

                TitleComp("Painel Administrativo", fontSize = 32.sp, backButton = false, buttonBackAction = {})

                Row(
                    Modifier.horizontalScroll(horizantalScrollState)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            text = "Comparação de valores pagos por mês",
                            color = TitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        BarPlotComp(
                            modifier = Modifier
                                .width(adaptiveWidth - 120.dp)
                                .padding(20.dp),
                            xData = months,
                            yData = totalByMonth
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 10.dp),
                    ) {
                        Text(
                            "Relação de parcelas pagas neste mês",
                            color = TitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        PieChartComp(
                            modifier = Modifier
                                .width(adaptiveWidth - 200.dp)
                                .padding(20.dp),
                            data = paymentData.y,
                            labels = paymentData.x
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            "Relação de propriedades alugadas",
                            color = TitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        PieChartComp(
                            modifier = Modifier
                                .width(adaptiveWidth - 200.dp)
                                .padding(20.dp),
                            data = rentData.y,
                            labels = rentData.x
                        )
                    }
                }
                Spacer(Modifier.height(30.dp))


                val highlightColor = PrimaryColor
                val backgroundColor = BackGroundColor

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    Alignment.Center
                ) {
                    LazyVerticalGrid(
                        columns = when (adaptiveWidth.value.toInt()) {
                            1100 -> GridCells.Fixed(3)
                            else -> GridCells.Fixed(2)
                        },
                        modifier = Modifier
                            .width(adaptiveWidth),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = false
                    ) {
                        item {
                            CardButtonComp(
                                text = "Propriedades",
                                icon = {
                                    Icon(
                                        Icons.Outlined.OtherHouses,
                                        contentDescription = "propriedades",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowProperties() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        item {
                            CardButtonComp(
                                text = "Locatários",
                                icon = {
                                    Icon(
                                        Icons.Outlined.PeopleOutline,
                                        contentDescription = "locatarios",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowTenants() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        item {
                            CardButtonComp(
                                text = "Relatos",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Feedback,
                                        contentDescription = "relatos",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowReports() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        item {
                            CardButtonComp(
                                text = "Cadastrar categoria",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "categoria",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToCreateCategory() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                        item {
                            CardButtonComp(
                                text = "Gerenciar Contratos",
                                icon = {
                                    Icon(
                                        Icons.Outlined.ManageAccounts,
                                        contentDescription = "contratos",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowLeases() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        item {
                            CardButtonComp(
                                text = "Ver perfil",
                                icon = {
                                    Icon(
                                        Icons.Outlined.ManageAccounts,
                                        contentDescription = "perfil",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToEditLandLord() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                    }
                }

            }
        }
    )
}

@Composable
fun CardButtonComp(
    text: String,
    width: Dp = 200.dp,
    icon : @Composable ()-> Unit,
    backgroundColor: Color = BackGroundColor,
    highlightColor:Color = PrimaryColor,
    action: ()-> Unit
){
    OutlinedCard(
        modifier = Modifier
            .width(width)
            .height(100.dp),
        onClick = action,

        ){
        Row(){
            Column(
                Modifier.background(highlightColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                icon()
            }

            Column(
                Modifier.background(backgroundColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFont(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TitleColor
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(HomeViewModel(navController))
}