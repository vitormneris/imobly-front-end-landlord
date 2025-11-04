package com.imobly.imobly.ui.screens.edit.editreport

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.ReportStatus
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.dropdown.DropdownComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.ReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditReportScreen(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = { SnackbarHost( reportViewModel.snackMessage.value ) },
        contentWindowInsets = WindowInsets.systemBars
    ){
            paddingValues ->
        Column (
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TitleComp("Editar relato",
                {reportViewModel.goToShowReports()}
            )

            Column (
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                InputComp(
                    label = "Título",
                    placeholder = "Ex: Goteira no quarto",
                    value = reportViewModel.report.value.title,
                    onValueChange = { reportViewModel.changeTitle(it) },
                    isError = reportViewModel.inputContainsError("title"),
                    errorMessage = reportViewModel.getInputErrorMessage("title"),
                    readOnly = true
                )
                InputComp(
                    label = "Autor",
                    placeholder = "Ex: Fernando",
                    value = reportViewModel.report.value.tenant.firstName,
                    onValueChange = {  },
                    isError = reportViewModel.inputContainsError("tenante"),
                    errorMessage = reportViewModel.getInputErrorMessage("tenant"),
                    readOnly = true
                )
                InputComp(
                    label = "Mensagem",
                    placeholder = "Ex: Está pingando muito no guarda roupa, preciso de ajuda.",
                    value = reportViewModel.report.value.message,
                    onValueChange = { reportViewModel.changeMessage(it) },
                    isError = reportViewModel.inputContainsError("message"),
                    errorMessage = reportViewModel.getInputErrorMessage("message"),
                    singleLine = false,
                    readOnly = true
                )
                InputComp(
                    label = "Minha resposta:",
                    placeholder = "Ex: Estou resolvendo, aguarde..",
                    value = reportViewModel.report.value.response,
                    onValueChange = { reportViewModel.changeResponse(it) },
                    isError = reportViewModel.inputContainsError("response"),
                    errorMessage = reportViewModel.getInputErrorMessage("response"),
                    singleLine = false,
                    readOnly = reportViewModel.inputLockState.value
                )

                DropdownComp(
                    label = "Status",
                    placeholder = "",
                    options = ReportStatus.entries.map { it.description },
                    selectedOption = reportViewModel.report.value.status.description,
                    onOptionSelected = {selectedOption-> reportViewModel.changeStatus(
                        ReportStatus.entries.first {it.description == selectedOption}
                    )
                    },
                    isEnabled = !reportViewModel.inputLockState.value
                )

                ConfirmDialogComp(
                    reportViewModel.showDialogState.value,
                    "Está ação deletará este relato",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { reportViewModel.deleteAction() },
                    { reportViewModel.changeShowDialog() }
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (reportViewModel.messageError.value != "") {
                        MessageErrorComp(reportViewModel.messageError.value, 14.sp)
                    }

                    if (reportViewModel.inputLockState.value) {
                        ButtonComp(
                            "Editar dados",
                            { Icon(Icons.Default.Edit, "editar") },
                            PrimaryColor,
                            { reportViewModel.hiddenEditButton() }
                        )
                        ButtonComp(
                            "Excluir",
                            { Icon(Icons.Default.Delete, "deletar") },
                            CancelColor,
                            { reportViewModel.changeShowDialog() }
                        )
                    } else {

                        if (reportViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else{
                            ButtonComp(
                                "Atualizar Status",
                                { Icon(Icons.Default.Check, "atualizar status") },
                                ConfirmColor,
                                { reportViewModel.updateStatusAction() }
                            )

                            ButtonComp(
                                "Enviar resposta",
                                { Icon(Icons.Default.Check, "enviar resposta") },
                                ConfirmColor,
                                { reportViewModel.replyToReportAction() }
                            )
                            ButtonComp(
                                "Cancelar",
                                { Icon(Icons.Default.Cancel, "cancelar") },
                                CancelColor,
                                { reportViewModel.hiddenEditButton() }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun EditReportScreenPreview() {
    val navControllerFake = rememberNavController()
    EditReportScreen(ReportViewModel(navControllerFake))
}