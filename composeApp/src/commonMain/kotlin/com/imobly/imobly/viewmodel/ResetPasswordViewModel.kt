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
import com.imobly.imobly.domain.Auth
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val navController: NavHostController): ViewModel()  {

    val email = mutableStateOf("")
    val newPassword = mutableStateOf("")
    val newPasswordConfirmation = mutableStateOf("")
    val code = mutableStateOf("")


    val passwordVisibilityState = mutableStateOf(false)
    val onLoadingState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    val messageError = mutableStateOf("")


    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun goToLogin() {
        navController.navigate("login")
    }

    fun goToInsertCode() {
        navController.navigate("insertcode")
    }

    fun goToChangePassword() {
        navController.navigate("changepassword")
    }


    fun changeEmail(it: String) {
        email.value = it
    }

    fun changeCode(it: String) {
        code.value = it
    }

    fun changeNewPassword(it: String) {
        newPassword.value = it
    }

    fun changeNewPasswordConfirmation(it: String) {
        newPasswordConfirmation.value = it
    }
}