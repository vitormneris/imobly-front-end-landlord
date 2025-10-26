package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.tenant.TenantHttpClient
import com.imobly.imobly.domain.Telephone
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch

class LoginViewModel(val navController: NavHostController): ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val passwordVisibilityState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
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

    fun createAction() {

    }

    fun changeEmail(it: String) {
        email.value = it
    }

    fun changePassword(it: String) {
        password.value = it
    }
}