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
import com.imobly.imobly.api.dto.UpdateReportDTO
import com.imobly.imobly.api.httpclient.ReportHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.enums.StatusReportEnum
import com.imobly.imobly.domain.Tenant
import kotlinx.coroutines.launch

class ReportViewModel(private val navController: NavController) : ViewModel() {

    val tenant = mutableStateOf(Tenant())
    val tenantPropertySelected = mutableStateOf(Property())
    val report = mutableStateOf(
        Report(
            tenant = tenant.value
        )
    )
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
        report.value = Report(tenant = tenant.value)
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            reports.value = httpClient.searchAllByTitleOrMessageOrName()
        }
    }

    fun goToEditReport(reportNew: Report) {
        changeProperty(reportNew.property)
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
            val list = httpClient.searchAllByTitleOrMessageOrName(searchText.value)
            reports.value = list
        }
    }

    fun updateAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = ReportHttpClient(createHttpClient())
            if (report.value.id != null) {
                val response = httpClient.update(report.value.id!!, UpdateReportDTO(report.value.status, report.value.response))
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        inputErrors.value = emptyMap()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Reportação respondida!")
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
                        messageError.value = response.message
                    }
                }
            }
        }
    }

    fun statusReportOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        StatusReportEnum.entries.forEach {
            map[it.label] = it.name
        }
        return map
    }

    fun changeResponse(it: String) {
        report.value = report.value.copy(response = it)
    }

    fun changeStatus(it: StatusReportEnum) {
        report.value = report.value.copy(status = it)
    }

    fun changeProperty(property: Property) {
        tenantPropertySelected.value = property
    }
}