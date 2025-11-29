package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.AuthenticationHttpClient
import com.imobly.imobly.api.httpclient.LandLordHttpClient
import com.imobly.imobly.domain.LandLord
import com.imobly.imobly.domain.Telephone
import kotlinx.coroutines.launch

class LandLordViewModel(private val navController: NavHostController) : ViewModel() {
    val landLord = mutableStateOf(LandLord())
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val passwordVisibilityState = mutableStateOf(false)
    val showDialogState = mutableStateOf(false)
    val onLoadingState = mutableStateOf(false)

    val inputLockState = mutableStateOf(true)

    val snackMessage: MutableState<SnackbarHostState> = mutableStateOf(SnackbarHostState())

    fun findProfile() {
        viewModelScope.launch {
            val httpClient = LandLordHttpClient(createHttpClient())
            val landLordFound = httpClient.getProfile()
            landLord.value = landLordFound
        }
    }

    fun signUpAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = AuthenticationHttpClient(createHttpClient())
            val response = httpClient.signUp(landLord.value)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    landLord.value = LandLord()
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    snackMessage.value.showSnackbar("Locatário cadastrado com sucesso!")
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

    fun editAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = LandLordHttpClient(createHttpClient())
            val response = httpClient.updateProfile(landLord.value)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    hiddenEditButton()
                    snackMessage.value.showSnackbar("Locatário editado com sucesso!")
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

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = LandLordHttpClient(createHttpClient())
            val response = httpClient.deleteProfile()
            showDialogState.value = false
            when (response) {
                is Ok -> {
                    snackMessage.value.showSnackbar("Locador deletado com sucesso!")
                }

                is ErrorDTO -> {
                    messageError.value = response.message
                }
            }
        }
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }

    fun resetPage() {
        landLord.value = LandLord()
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun goToLogin() {
        navController.navigate("login")
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun changeFirstName(it: String) {
        landLord.value = landLord.value.copy(firstName = it)
    }

    fun changeLastName(it: String) {
        landLord.value = landLord.value.copy(lastName = it)
    }

    fun changeEmail(it: String) {
        landLord.value = landLord.value.copy(email = it)
    }

    fun changeTelephoneOne(it: String) {
        landLord.value = landLord.value.copy(
            telephones = Telephone(
                it,
                landLord.value.telephones.telephone2,
                landLord.value.telephones.telephone3
            )
        )
    }

    fun changeTelephoneTwo(it: String) {
        landLord.value = landLord.value.copy(
            telephones = Telephone(
                landLord.value.telephones.telephone1,
                it,
                landLord.value.telephones.telephone3
            )
        )
    }

    fun changeTelephoneThree(it: String) {
        landLord.value = landLord.value.copy(
            telephones = Telephone(
                landLord.value.telephones.telephone1,
                landLord.value.telephones.telephone2,
                it
            )
        )
    }

    fun changePassword(it: String) {
        landLord.value = landLord.value.copy(password = it)
    }
}