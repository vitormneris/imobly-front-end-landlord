package com.imobly.imobly.ui.screens.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.CategoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryScreen(viewModel: CategoryViewModel) {

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(viewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TitleComp(
                text = "Cadastrar Categoria",
                buttonBackAction = { viewModel.goToShowCategories() }
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ===== INPUT: TÍTULO =====
                InputComp(
                    label = "Nome da Categoria",
                    placeholder = "Ex: Residencial",
                    value = viewModel.category.value.title,
                    onValueChange = { viewModel.changeTitle(it) },
                    isError = viewModel.errors.containsKey("title"),
                    errorMessage = viewModel.errors["title"] ?: ""
                )

                Spacer(Modifier.height(16.dp))

                // ===== DROPDOWN =====
                DropdownComp(
                    label = "Adicionar Propriedade",
                    placeholder = "Digite uma propriedade...",
                    options = viewModel.properties.value,
                    selectedOption = viewModel.propertyText.value,
                    onOptionSelected = { viewModel.changePropertyText(it) },
                    isEnabled = true
                )

                Spacer(Modifier.height(10.dp))

                // Botão ADICIONAR PROPRIEDADE
                Button(
                    onClick = { viewModel.addProperty() },
                    colors = ButtonDefaults.buttonColors(PrimaryColor),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Adicionar propriedade")
                }

                Spacer(Modifier.height(18.dp))

                // ===== LISTA DE PROPRIEDADES =====
                if (viewModel.properties.value.isNotEmpty()) {
                    Text(
                        "Propriedades adicionadas:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 250.dp)
                    ) {
                        itemsIndexed(viewModel.properties.value) { index, prop ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(prop, fontSize = 15.sp)
                                IconButton(onClick = { viewModel.removeProperty(index) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remover")
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                // ===== BOTÃO SALVAR =====
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (viewModel.onLoadingState.value) {
                        CircularProgressIndicator()
                    } else {

                        if (viewModel.messageError.value.isNotEmpty()) {
                            MessageErrorComp(
                                message = viewModel.messageError.value,
                                fontSize = 14.sp
                            )
                        }

                        ButtonComp(
                            "Cadastrar Categoria",
                            { Icon(Icons.Default.Create, contentDescription = null) },
                            PrimaryColor
                        ) {
                            viewModel.createAction()
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun CreateCategoryScreenPreview() {
    val nav = rememberNavController()
    CreateCategoryScreen(CategoryViewModel(nav))
}