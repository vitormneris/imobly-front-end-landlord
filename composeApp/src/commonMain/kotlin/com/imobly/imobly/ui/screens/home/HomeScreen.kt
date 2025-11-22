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
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
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
    val verticalScrollState = rememberScrollState()

    val horizontalScrollState = rememberScrollState()

    homeViewModel.loadGraphics()

    Scaffold(
        topBar = {
            TopBarComp()
        },
       snackbarHost = { SnackbarHost( homeViewModel.snackMessage.value ) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(verticalScrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val adaptiveInfo = currentWindowAdaptiveInfo()
            val adaptiveWidth = when {
                adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> 1100.dp
                adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> 700.dp
                else -> 600.dp
            }

            TitleComp("Painel Administrativo", fontSize = 32.sp, backButton = false, buttonBackAction = {})

            Row(
                Modifier.horizontalScroll(horizontalScrollState)
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
                        xData = homeViewModel.rentByMonth.value.x,
                        yData = homeViewModel.rentByMonth.value.y
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
                        data = homeViewModel.rentsPaidThisMonth.value.y,
                        labels = homeViewModel.rentsPaidThisMonth.value.x
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
                        data = homeViewModel.rentedProperties.value.y,
                        labels = homeViewModel.rentedProperties.value.x
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
                            text = "Reportações",
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

                        item {
                            CardButtonComp(
                                text = "Forgot password",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "esqueci a senha",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToForgotPassword() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                        )
                    }

                        item {
                            CardButtonComp(
                                text = "Insert Code",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "codigo",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToInsertCode() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                        item {
                            CardButtonComp(
                                text = "Change password",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "mudar senha",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToChangePassword() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                        )
                    }
                }
            }
        }
    }
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