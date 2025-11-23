package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Payment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState

class PaymentViewModel(private val nav: NavHostController? = null) : ViewModel() {

    // Lista de pagamentos
    var payments = mutableStateOf(listOf<Payment>())

    // Pagamento atual
    var payment = mutableStateOf(Payment())

    // Snackbar
    val snackMessage = mutableStateOf(SnackbarHostState())

    // Estados para busca
    val searchText = mutableStateOf("")
    fun changeSearchText(text: String) { searchText.value = text }

    fun searchAction() {
        payments.value = payments.value.filter {
            it.status.contains(searchText.value, true) ||
                    it.issueDate.contains(searchText.value, true) ||
                    it.dueDate.contains(searchText.value, true)
        }
    }

    fun findAllAction() {
        // Mock temporário
        if (payments.value.isEmpty()) {
            payments.value = listOf(
                Payment("01/02/2025", "10/02/2025", 1500.0, "", "", "Pendente"),
                Payment("05/02/2025", "15/02/2025", 2200.0, "", "", "Pago")
            )
        }
    }

    // Carregamento
    val onLoadingState = mutableStateOf(false)

    // Erro geral
    val messageError = mutableStateOf("")

    // Validação dos inputs
    private val inputErrors = mutableMapOf<String, String>()

    fun inputContainsError(field: String): Boolean =
        inputErrors[field]?.isNotEmpty() == true

    fun getInputErrorMessage(field: String): String =
        inputErrors[field] ?: ""

    private fun validateFields(): Boolean {
        inputErrors.clear()

        if (payment.value.issueDate.isBlank()) inputErrors["issueDate"] = "Informe a data de emissão."
        if (payment.value.dueDate.isBlank()) inputErrors["dueDate"] = "Informe o vencimento."
        if (payment.value.rentalValue <= 0) inputErrors["rentalValue"] = "Valor inválido."
        if (payment.value.status.isBlank()) inputErrors["status"] = "Informe o status."

        return inputErrors.isEmpty()
    }

    // Alterar campos
    fun changeIssueDate(v: String) = update("issueDate", v)
    fun changeDueDate(v: String) = update("dueDate", v)
    fun changeRentalValue(v: String) = update("rentalValue", v)
    fun changeStatus(v: String) = update("status", v)

    fun update(field: String, value: String) {
        payment.value = when (field) {
            "issueDate" -> payment.value.copy(issueDate = value)
            "dueDate" -> payment.value.copy(dueDate = value)
            "moment" -> payment.value.copy(moment = value)
            "qrcode" -> payment.value.copy(qrcode = value)
            "status" -> payment.value.copy(status = value)
            "rentalValue" -> payment.value.copy(rentalValue = value.toDoubleOrNull() ?: 0.0)
            else -> payment.value
        }
    }

    // Quando abre a tela de edição
    fun whenStartingThePage() {
        messageError.value = ""
        inputErrors.clear()
    }

    // Atualizar pagamento
    fun updateAction() {
        MainScope().launch {
            onLoadingState.value = true
            messageError.value = ""

            if (!validateFields()) {
                onLoadingState.value = false
                return@launch
            }

            val updated = payments.value.map {
                if (it.issueDate == payment.value.issueDate &&
                    it.dueDate == payment.value.dueDate &&
                    it.rentalValue == payment.value.rentalValue
                ) payment.value else it
            }

            payments.value = updated

            onLoadingState.value = false
            snackMessage.value.showSnackbar("Pagamento atualizado!")

            nav?.popBackStack()
        }
    }

    fun goToHome() {
        nav?.navigate("home")
    }

    fun goToOpen(p: Payment) {
        payment.value = p
        nav?.navigate("openpayment")
    }
}