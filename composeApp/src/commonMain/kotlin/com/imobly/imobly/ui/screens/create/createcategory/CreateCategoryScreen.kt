package com.imobly.imobly.ui.screens.create.createcategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.CategoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateCategoryScreen(categoryViewModel: CategoryViewModel) {
    val scrollState = rememberScrollState()

    categoryViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(categoryViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComp("Cadastrar Categoria", { categoryViewModel.goToHome() })

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputComp(
                    label = "Nome da Categoria",
                    placeholder = "Ex: Residencial",
                    value = categoryViewModel.category.value.title,
                    onValueChange = { categoryViewModel.changeTitle(it) },
                    isError = categoryViewModel.inputContainsError("title"),
                    errorMessage = categoryViewModel.getInputErrorMessage("title")
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (categoryViewModel.onLoadingState.value) {
                        Box(Modifier.padding(20.dp)) {
                            CircularProgressIndicator()
                        }
                    } else {
                        if (categoryViewModel.messageError.value.isNotEmpty()) {
                            MessageErrorComp(
                                message = categoryViewModel.messageError.value,
                                fontSize = 14.sp
                            )
                        }
                        Box(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .height(120.dp)
                        ) {
                            ButtonComp(
                                "Cadastrar Categoria",
                                { Icon(Icons.Default.Create, contentDescription = "Salvar") },
                                PrimaryColor,
                                { categoryViewModel.createAction() }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun CreateCategoryScreenPreview() {
    val navControllerFake = rememberNavController()
    CreateCategoryScreen(CategoryViewModel(navControllerFake))
}