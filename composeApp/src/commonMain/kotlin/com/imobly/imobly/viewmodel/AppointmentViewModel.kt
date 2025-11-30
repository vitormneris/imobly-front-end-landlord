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
import com.imobly.imobly.api.httpclient.AppointmentHttpClient
import com.imobly.imobly.api.httpclient.ReportHttpClient
import com.imobly.imobly.domain.Appointment
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.enums.StatusReportEnum
import com.imobly.imobly.domain.Tenant
import kotlinx.coroutines.launch

class AppointmentViewModel(private val navController: NavController) : ViewModel() {

    val appointment = mutableStateOf(Appointment())
    val appointmentPropertySelected = mutableStateOf(Property())

    val inputLockState = mutableStateOf(true)
    val onLoadingState = mutableStateOf(false)
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val showDialogState = mutableStateOf(false)
    val searchText: MutableState<String> = mutableStateOf("")
    val appointments: MutableState<List<Appointment>> = mutableStateOf(emptyList())
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
        appointment.value = Appointment()
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = AppointmentHttpClient(createHttpClient())
            appointments.value = httpClient.searchAllByTitle()
        }
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowReports() {
        navController.navigate("showappointments")
    }

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = AppointmentHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitle(searchText.value)
            appointments.value = list
        }
    }

    fun deleteAction(appointmentToDelete: Appointment) {
        viewModelScope.launch {
            val httpClient = AppointmentHttpClient(createHttpClient())
            val response = httpClient.delete(appointmentToDelete.id)
            showDialogState.value = false
            when (response) {
                is Ok -> {
                    goToShowReports()
                    snackMessage.value.showSnackbar("Agendamento deletado com sucesso!")
                }

                is ErrorDTO -> {
                    messageError.value = response.message
                }
            }
        }
    }

    fun changeProperty(property: Property) {
        appointmentPropertySelected.value = property
    }
}