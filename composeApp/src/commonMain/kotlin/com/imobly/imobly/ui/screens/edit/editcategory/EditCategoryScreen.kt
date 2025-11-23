package com.imobly.imobly.ui.screens.edit.editcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.CategoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditCategoryScreen(categoryViewModel: CategoryViewModel) {
    val scrollState = rememberScrollState()

    categoryViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(categoryViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TitleComp("Editar Categoria", { categoryViewModel.goToShowCategories() }, true)

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputComp(
                    label = "Título",
                    placeholder = "Ex: Residencial",
                    value = categoryViewModel.category.value.title,
                    onValueChange = { categoryViewModel.changeTitle(it) },
                    isError = categoryViewModel.inputContainsError("title"),
                    errorMessage = categoryViewModel.getInputErrorMessage("title"),
                    readOnly = categoryViewModel.inputLockState.value
                )

                Spacer(Modifier.height(20.dp))

                ConfirmDialogComp(
                    categoryViewModel.showDialogState.value,
                    "Está ação deletará a categoria",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { categoryViewModel.deleteAction() },
                    { categoryViewModel.changeShowDialog() }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (categoryViewModel.messageError.value != "") {
                        MessageErrorComp(categoryViewModel.messageError.value, 16.sp)
                    }
                    if (categoryViewModel.inputLockState.value) {

                        FlowRow {
                            ButtonComp(
                                "Editar dados",
                                { Icon(Icons.Default.Edit, "editar") },
                                PrimaryColor,
                                { categoryViewModel.hiddenEditButton() },
                                185.dp,
                                16.sp
                            )

                            ButtonComp(
                                "Excluir",
                                { Icon(Icons.Default.Delete, "deletar") },
                                CancelColor,
                                { categoryViewModel.changeShowDialog() },
                                140.dp,
                                16.sp
                            )
                        }

                    } else {
                        if (categoryViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {
                            FlowRow {
                                ButtonComp(
                                    "Salvar",
                                    { Icon(Icons.Default.Check, "confirmar") },
                                    ConfirmColor,
                                    { categoryViewModel.editAction() },
                                    140.dp,
                                    16.sp
                                )

                                ButtonComp(
                                    "Cancelar",
                                    { Icon(Icons.Default.Cancel, "cancelar") },
                                    CancelColor,
                                    { categoryViewModel.hiddenEditButton() },
                                    155.dp,
                                    16.sp
                                )
                            }
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