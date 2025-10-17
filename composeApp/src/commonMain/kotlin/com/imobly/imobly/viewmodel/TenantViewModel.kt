package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.property.PropertyHttpClient
import com.imobly.imobly.api.tenant.TenantHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.ResponseMessage
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch

class TenantViewModel(val navController: NavHostController): ViewModel() {

    val tenants: MutableState<List<Tenant>> = mutableStateOf(emptyList())

    val tenant = mutableStateOf(Tenant())
    val searchText: MutableState<String> = mutableStateOf("")

    val selectedImage = mutableStateOf(GalleryPhotoResult(
        "n",1,1
    ))

    val inputLockState = mutableStateOf(true)

    val onLoadingState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())

    val showDialogState = mutableStateOf(false)

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }
    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            tenants.value = httpClient.searchAll()
        }
    }

    fun goToEditTenant(newTenant: Tenant) {
        tenant.value = newTenant
        navController.navigate("edittenant")
    }
    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowTenants() {
        navController.navigate("showtenants")
    }


    fun editAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = TenantHttpClient(createHttpClient())
            var response = ResponseMessage(status = 500)
            if (tenant.value.id != null) {
                response = httpClient.update(tenant.value.id!!, tenant.value, selectedImage.value)
            }
            onLoadingState.value = false
            if (response.status == 200) {
                hiddenEditButton()
                snackMessage.value.showSnackbar("Locatário editado com sucesso!")
            } else {
                val errors = mutableMapOf<String, String>()
                response.errorFields?.forEach { errors[it.name] = it.description }
                inputErrors.value = errors
            }
        }
    }
}