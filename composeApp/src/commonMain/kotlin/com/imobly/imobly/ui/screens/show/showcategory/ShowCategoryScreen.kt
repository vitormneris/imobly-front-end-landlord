package com.imobly.imobly.ui.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.CategoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowCategoriesScreen(viewModel: CategoryViewModel) {

    val categories by viewModel.categories
    val search by viewModel.searchText

    LaunchedEffect(Unit) {
        viewModel.findAllAction()
    }

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(viewModel.snackMessage.value) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
        ) {

            TitleComp(
                text = "Categorias",
                buttonBackAction = { viewModel.goToHome() }
            )

            // Botão de cadastrar nova categoria
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ButtonComp(
                    "Cadastrar categoria",
                    { Icon(Icons.Default.Add, contentDescription = null) },
                    PrimaryColor
                ) { viewModel.goToCreateCategory() }
            }

            // Barra de busca
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                SearchBarComp(
                    label = "Buscar uma categoria",
                    value = search,
                    onValueChange = { viewModel.changeSearchText(it) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de categorias
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(categories) { category ->
                    CategoryCardComp(category, viewModel)
                }
            }
        }
    }
}

@Composable
fun CategoryCardComp(
    category: com.imobly.imobly.domain.Category,
    viewModel: CategoryViewModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .widthIn(max = 1000.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = category.title,
                fontSize = 18.sp,
                color = Color(0xFF2D5B7A),
                fontFamily = montserratFont()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                if (category.properties.isEmpty()) {
                    Text(
                        text = "Nenhuma propriedade vinculada",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = montserratFont()
                    )
                } else {
                    category.properties.forEach { prop ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(
                                        Color(0xFFF2603F).copy(alpha = 0.2f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFFF2603F)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = prop.title,
                                fontSize = 14.sp,
                                color = Color(0xFF333333),
                                fontFamily = montserratFont()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = { viewModel.goToEditCategory(category) },
                    modifier = Modifier
                        .height(42.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF2603F)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Ver Detalhes",
                        fontSize = 14.sp,
                        fontFamily = montserratFont()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = { viewModel.deleteAction(category) },
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            Color(0xFFF2603F).copy(alpha = 0.15f),
                            CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Excluir",
                        tint = Color(0xFFF2603F)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowCategoriesPreview() {
    val nav = rememberNavController()
    ShowCategoriesScreen(CategoryViewModel(nav))
}