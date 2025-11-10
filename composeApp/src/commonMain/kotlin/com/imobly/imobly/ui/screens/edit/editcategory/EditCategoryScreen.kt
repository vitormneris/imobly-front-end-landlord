package com.imobly.imobly.ui.screens.edit.editcategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.CategoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditCategoryScreen(viewModel: CategoryViewModel) {
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

            TitleComp("Editar Categoria", { viewModel.goToShowCategories() })

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ===== INPUT: NOME =====
                InputComp(
                    label = "Nome da Categoria",
                    placeholder = "Ex: Residencial",
                    value = viewModel.category.value.title,
                    onValueChange = { viewModel.changeTitle(it) },
                    isError = viewModel.inputContainsError("title"),
                    errorMessage = viewModel.getInputErrorMessage("title"),
                    readOnly = viewModel.inputLockState.value
                )

                // ===== DROPDOWN + PROPRIEDADES =====
                DropdownComp(
                    label = "Propriedade",
                    placeholder = "Selecione ou digite...",
                    options = viewModel.properties.value,
                    selectedOption = viewModel.propertyText.value,
                    onOptionSelected = { selected -> viewModel.changePropertyText(selected) },
                    isEnabled = !viewModel.inputLockState.value
                )

                if (viewModel.properties.value.isNotEmpty()) {
                    Text("Propriedades adicionadas:", style = MaterialTheme.typography.titleMedium)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                    ) {
                        itemsIndexed(viewModel.properties.value) { index, prop ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(prop)
                                if (!viewModel.inputLockState.value) {
                                    IconButton(onClick = { viewModel.removeProperty(index) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remover")
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ===== AÇÕES =====
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (viewModel.messageError.value.isNotEmpty()) {
                        MessageErrorComp(viewModel.messageError.value, 14.sp)
                    }

                    if (viewModel.inputLockState.value) {
                        ButtonComp(
                            "Editar dados",
                            { Icon(Icons.Default.Edit, "editar") },
                            PrimaryColor
                        ) { viewModel.hiddenEditButton() }

                        ButtonComp(
                            "Excluir",
                            { Icon(Icons.Default.Delete, "deletar") },
                            CancelColor
                        ) { viewModel.changeShowDialog() }

                    } else {

                        if (viewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) { CircularProgressIndicator() }
                        } else {
                            ButtonComp(
                                "Atualizar Categoria",
                                { Icon(Icons.Default.Check, "atualizar") },
                                ConfirmColor
                            ) { viewModel.updateAction() }

                            ButtonComp(
                                "Cancelar",
                                { Icon(Icons.Default.Cancel, "cancelar") },
                                CancelColor
                            ) { viewModel.hiddenEditButton() }
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
fun EditCategoryScreenPreview() {
    val navController = rememberNavController()
    EditCategoryScreen(CategoryViewModel(navController))
}