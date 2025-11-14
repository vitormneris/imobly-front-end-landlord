package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseReportDTO
import com.imobly.imobly.api.dto.StatusReportDTO
import com.imobly.imobly.api.httpclient.ReportHttpClient
import com.imobly.imobly.api.httpclient.TenantHttpClient
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.enums.StatusReportEnum
import com.imobly.imobly.domain.Tenant
import com.imobly.imobly.domain.enums.MaritalStatusEnum
import kotlinx.coroutines.launch

class ReportViewModel(private val navController: NavController): ViewModel() {

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

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
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
            reports.value = httpClient.searchAllByTitleOrMessage()
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

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitleOrMessage(searchText.value)
            reports.value = list
        }
    }

    fun replyToReportAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            if (report.value.id != null) {
                onLoadingState.value = true
                val response = httpClient.replyToReport(report.value.id!!, ResponseReportDTO(report.value.response))
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        inputErrors.value = emptyMap()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Resposta enviada!")
                    }

                    is ErrorDTO -> {
                        val errors = mutableMapOf<String, String>()
                        response.errorFields?.forEach { errors[it.name] = it.description }
                        inputErrors.value = errors
                        messageError.value = response.message
                    }
                }
            }
        }
    }

    fun updateStatusAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = ReportHttpClient(createHttpClient())
            if (report.value.id != null) {
                val response = httpClient.updateStatus(report.value.id!!, StatusReportDTO(report.value.status) )
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        inputErrors.value = emptyMap()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Status atualizado!")
                    }
                    is ErrorDTO -> {
                        val errors = mutableMapOf<String, String>()
                        response.errorFields?.forEach { errors[it.name] = it.description }
                        inputErrors.value = errors
                        messageError.value = response.message
                    }
                }
            }
        }
    }

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            if (report.value.id != null) {
                val response = httpClient.delete(report.value.id!!)
                showDialogState.value = false
                goToShowReports()
                when (response) {
                    is Ok -> {
                        snackMessage.value.showSnackbar("Relato deletado com sucesso!")
                    }
                    is ErrorDTO -> {
                        snackMessage.value.showSnackbar("Houve um problema ao deletar o relato!")
                    }
                }
            }
        }
    }

    fun statusReportOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        StatusReportEnum.entries.forEach {
            map[it.description] = it.name
        }
        return map
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

    fun changeStatus(it: StatusReportEnum) {
        report.value = report.value.copy(status = it)
    }
}