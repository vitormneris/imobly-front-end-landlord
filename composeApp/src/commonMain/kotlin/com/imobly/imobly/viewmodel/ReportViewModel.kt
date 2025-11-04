package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.report.ReportHttpClient
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.ReportStatus
import com.imobly.imobly.domain.ResponseMessage
import com.imobly.imobly.domain.Tenant
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ReportViewModel(val navController: NavController): ViewModel() {

    val tenant = mutableStateOf(Tenant())
    val report = mutableStateOf(Report(
        tenant = tenant.value
    ))
    val inputLockState = mutableStateOf(true)
    val onLoadingState = mutableStateOf(false)
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val showDialogState = mutableStateOf(false)
    val searchText: MutableState<String> = mutableStateOf("")
    val reports: MutableState<List<Report>> = mutableStateOf(emptyList())
    val snackMessage: MutableState<SnackbarHostState> = mutableStateOf(SnackbarHostState())


    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }

    fun resetPage() {
        tenant.value = Tenant()
        report.value = Report(tenant= tenant.value)
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            reports.value = httpClient.searchAll()
        }
    }

    fun goToEditReport(reportNew: Report) {
        report.value = reportNew
        inputLockState.value = true
        inputErrors.value = emptyMap()
        messageError.value = ""
        navController.navigate("editreport")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowReports() {
        navController.navigate("showreports")
    }

    fun replyToReportAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = ReportHttpClient(createHttpClient())
            var response = ResponseMessage(status = 500)
            if (report.value.id != null) {
                response = httpClient.replyToReport(report.value.id!!, report.value.response )
            }
            onLoadingState.value = false
            if (response.status == 200) {
                hiddenEditButton()
                inputErrors.value = emptyMap()
                messageError.value = ""
                snackMessage.value.showSnackbar("Resposta enviada!")
            } else {
                val errors = mutableMapOf<String, String>()
                response.errorFields?.forEach { errors[it.name] = it.description }
                inputErrors.value = errors
                messageError.value = response.message
            }
        }
    }

    fun updateStatusAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = ReportHttpClient(createHttpClient())
            var response = ResponseMessage(status = 500)
            if (report.value.id != null) {
                response = httpClient.updateStatus(report.value.id!!, report.value.status )
            }
            onLoadingState.value = false
            if (response.status == 200) {
                hiddenEditButton()
                inputErrors.value = emptyMap()
                messageError.value = ""
                snackMessage.value.showSnackbar("Status atualizado!")
            } else {
                val errors = mutableMapOf<String, String>()
                response.errorFields?.forEach { errors[it.name] = it.description }
                inputErrors.value = errors
                messageError.value = response.message
            }
        }
    }

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            var success = false
            if (report.value.id != null) {
                success = httpClient.delete(report.value.id!!)
            }
            showDialogState.value = false
            goToShowReports()
            if (success) {
                snackMessage.value.showSnackbar("Relato deletado com sucesso!")
            } else {
                snackMessage.value.showSnackbar("Houve um problema ao deletar o relato!")
            }
        }
    }

    fun changeTitle(it: String) {
        report.value = report.value.copy(title = it)
    }

    fun changeMessage(it: String) {
        report.value = report.value.copy(message = it)
    }

    fun changeResponse(it: String) {
        report.value = report.value.copy(response = it)
    }

    fun changeStatus(it: ReportStatus) {
        report.value = report.value.copy(status = it)
    }


}