package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Payment
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.PaymentStatusDTO
import com.imobly.imobly.api.httpclient.PaymentHttpClient
import com.imobly.imobly.domain.Lease
import com.imobly.imobly.domain.MonthlyInstallment
import com.imobly.imobly.domain.enums.PaymentStatusEnum
import kotlinx.coroutines.launch

class PaymentViewModel(private val navController: NavHostController) : ViewModel() {
    val payment = mutableStateOf(Payment())

    val monthlyInstallment = mutableStateOf(MonthlyInstallment())

    val snackMessage = mutableStateOf(SnackbarHostState())

    val searchText = mutableStateOf("")

    val inputLockState = mutableStateOf(true)

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }

    fun changeStatus(it: PaymentStatusEnum) {
        monthlyInstallment.value = monthlyInstallment.value.copy(status = it)
    }

    fun changeSearchText(text: String) {
        searchText.value = text
    }

    fun searchAction() {

    }

    fun findAllAction() {

    }

    val onLoadingState = mutableStateOf(false)

    val messageError = mutableStateOf("")

    private val inputErrors = mutableMapOf<String, String>()

    fun inputContainsError(field: String): Boolean =
        inputErrors[field]?.isNotEmpty() == true

    fun getInputErrorMessage(field: String): String =
        inputErrors[field] ?: ""


    fun whenStartingThePage() {
        messageError.value = ""
        inputErrors.clear()
    }

    fun updateAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PaymentHttpClient(createHttpClient())
            if (payment.value.id != null){
                val response = httpClient.updateStatus(payment.value.id!!, monthlyInstallment.value.id!!, PaymentStatusDTO(monthlyInstallment.value.status))
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        inputErrors.values.clear()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Status atualizado!")
                    }
                    is ErrorDTO -> {
                        val errors = mutableMapOf<String, String>()
                        response.errorFields?.forEach { errors[it.name] = it.description }
                        inputErrors.putAll(errors)
                        messageError.value = response.message
                    }
                }

            }
        }
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToInstallment(monthlyInstallmentNew: MonthlyInstallment) {
        monthlyInstallment.value = monthlyInstallmentNew
        navController.navigate("installment")
    }

    fun goToShowPayments() {
        navController.navigate("showpayments")
    }

    fun goToShowLeases() {
        navController.navigate("showleases")
    }
}