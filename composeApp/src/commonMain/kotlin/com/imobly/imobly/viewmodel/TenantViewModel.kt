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
import com.imobly.imobly.domain.Telephone
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch

class TenantViewModel(val navController: NavHostController): ViewModel() {

    val tenants: MutableState<List<Tenant>> = mutableStateOf(emptyList())

    val tenant = mutableStateOf(Tenant())

    val telephoneOne = mutableStateOf("")
    val telephoneTwo = mutableStateOf("")
    val telephoneThree = mutableStateOf("")

    val searchText: MutableState<String> = mutableStateOf("")

    val selectedImages = mutableStateOf(emptyList<GalleryPhotoResult>())

    val inputLockState = mutableStateOf(true)

    val onLoadingState = mutableStateOf(false)

    val passwordVisibilityState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")

    val showDialogState = mutableStateOf(false)

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun resetPage() {
        tenant.value = Tenant()
        selectedImages.value = emptyList()
        inputErrors.value = emptyMap()
        messageError.value = ""
        telephoneOne.value = ""
        telephoneTwo.value = ""
        telephoneThree.value = ""
        searchText.value = ""
    }

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
        inputLockState.value = true
        inputErrors.value = emptyMap()
        messageError.value = ""
        selectedImages.value = emptyList()

        when (tenant.value.telephones.size) {
            1 -> {
                telephoneOne.value = tenant.value.telephones[0].telephone ?: ""
            }
            2 -> {
                telephoneOne.value = tenant.value.telephones[0].telephone ?: ""
                telephoneTwo.value = tenant.value.telephones[1].telephone ?: ""
            }
            3 -> {
                telephoneOne.value = tenant.value.telephones[0].telephone ?: ""
                telephoneTwo.value = tenant.value.telephones[1].telephone ?: ""
                telephoneThree.value = tenant.value.telephones[2].telephone ?: ""
            }
        }

        navController.navigate("edittenant")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowTenants() {
        navController.navigate("showtenants")
    }

    fun goToCreateTenant() {
        navController.navigate("createtenant")
    }

    fun createAction() {
        tenant.value.telephones = mutableListOf()
        val telephones: Array<String> = arrayOf(telephoneOne.value, telephoneTwo.value, telephoneThree.value)
        telephones.forEach {
            if (it != "") tenant.value.telephones.add(Telephone(it))
        }

        if (selectedImages.value.isNotEmpty()) {
            viewModelScope.launch {
                onLoadingState.value = true
                val httpClient = TenantHttpClient(createHttpClient())
                var imageToSend: GalleryPhotoResult? = null
                if (selectedImages.value.isNotEmpty()) {
                    imageToSend = selectedImages.value.first()
                }
                val response = httpClient.create(tenant.value, imageToSend)
                onLoadingState.value = false
                if (response.status == 200) {
                    tenant.value = Tenant()
                    selectedImages.value = emptyList()
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    telephoneOne.value = ""
                    telephoneTwo.value = ""
                    telephoneThree.value = ""
                    snackMessage.value.showSnackbar("Locatário salvo com sucesso!")
                } else {
                    val errors = mutableMapOf<String, String>()
                    response.errorFields?.forEach { errors[it.name] = it.description }
                    inputErrors.value = errors
                    messageError.value = response.message
                }
            }
        } else {
            messageError.value = "Você deve enviar uma imagem"
        }
    }

    fun editAction() {
        tenant.value.telephones = mutableListOf()
        val telephones: Array<String> = arrayOf(telephoneOne.value, telephoneTwo.value, telephoneThree.value)
        telephones.forEach {
            if (it != "") tenant.value.telephones.add(Telephone(it))
        }

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = TenantHttpClient(createHttpClient())
            var response = ResponseMessage(status = 500)
            if (tenant.value.id != null) {
                var imageToSend: GalleryPhotoResult? = null
                if (selectedImages.value.isNotEmpty()) {
                    imageToSend = selectedImages.value.first()
                }
                response = httpClient.update(tenant.value.id!!, tenant.value, imageToSend)
            }
            onLoadingState.value = false
            if (response.status == 200) {
                hiddenEditButton()
                snackMessage.value.showSnackbar("Locatário editado com sucesso!")
            } else {
                val errors = mutableMapOf<String, String>()
                response.errorFields?.forEach { errors[it.name] = it.description }
                inputErrors.value = errors
                messageError.value = response.message
            }
        }
    }

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            var success = false
            if (tenant.value.id != null) {
                success = httpClient.delete(tenant.value.id!!)
            }
            showDialogState.value = false
            goToShowTenants()
            if (success) {
                snackMessage.value.showSnackbar("Locatário deletado com sucesso!")
            } else {
                snackMessage.value.showSnackbar("Houve um problema ao deletar a locatário!")
            }
        }
    }

    fun changeFirstName(it: String) {
        tenant.value = tenant.value.copy(firstName = it)
    }

    fun changeLastName(it: String) {
        tenant.value = tenant.value.copy(lastName = it)
    }

    fun changeEmail(it: String) {
        tenant.value = tenant.value.copy(email = it)
    }

    fun changeTelephoneOne(it: String) {
        telephoneOne.value = it
    }

    fun changeTelephoneTwo(it: String) {
        telephoneTwo.value = it
    }

    fun changeTelephoneThree(it: String) {
        telephoneThree.value = it
    }

    fun changePassword(it: String) {
        tenant.value = tenant.value.copy(password = it)
    }

    fun changeNationality(it: String) {
        tenant.value = tenant.value.copy(nationality = it)
    }

    fun changeCpf(it: String) {
        tenant.value = tenant.value.copy(cpf = it)
    }

    fun changeRg(it: String) {
        tenant.value = tenant.value.copy(rg = it)
    }

    fun changeBirthDate(it: String) {
        tenant.value = tenant.value.copy(birthDate = it)
    }

    fun changeCep(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(cep = it)
        )
    }

    fun changeState(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(state = it)
        )
    }

    fun changeCity(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(city = it)
        )
    }

    fun changeNeighborhood(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(neighborhood = it)
        )
    }

    fun changeStreet(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(street = it)
        )
    }

    fun changeNumber(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(
                number = if (it.toIntOrNull() != null || it == "") {
                    it
                } else {
                    tenant.value.address.number
                }
            )
        )
    }

    fun changeComplement(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(complement = it)
        )
    }
}