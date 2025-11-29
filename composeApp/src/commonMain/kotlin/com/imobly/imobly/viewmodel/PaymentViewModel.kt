package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Payment
import androidx.compose.material3.SnackbarHostState
import com.imobly.imobly.domain.MonthlyInstallment

class PaymentViewModel(private val navController: NavHostController) : ViewModel() {
    val payment = mutableStateOf(Payment())

    val monthlyInstallment = mutableStateOf(MonthlyInstallment())

    val snackMessage = mutableStateOf(SnackbarHostState())

    val searchText = mutableStateOf("")

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