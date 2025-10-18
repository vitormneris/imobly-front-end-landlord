package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.property.PropertyHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.ResponseMessage
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class PropertyViewModel(val navController: NavHostController): ViewModel() {
    val property = mutableStateOf(Property())
    val selectedImages = mutableStateOf(emptyList<GalleryPhotoResult>())

    val inputLockState = mutableStateOf(true)

    val onLoadingState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")

    val showDialogState = mutableStateOf(false)

    val searchText: MutableState<String> = mutableStateOf("")
    val properties: MutableState<List<Property>> = mutableStateOf(emptyList())

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

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

    fun resetPage() {
        property.value = Property()
        selectedImages.value = emptyList()
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            properties.value = httpClient.searchAll()
        }
    }

    fun goToEditProperty(propertyNew: Property) {
        property.value = propertyNew
        selectedImages.value = emptyList()
        inputLockState.value = true
        inputErrors.value = emptyMap()
        messageError.value = ""
        navController.navigate("editproperty")
    }
    fun goToCreateProperty() {
        navController.navigate("createproperty")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowProperties() {
        navController.navigate("showproperties")
    }

    fun editAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PropertyHttpClient(createHttpClient())
            var response = ResponseMessage(status = 500)
            if (property.value.id != null) {
                response = httpClient.update(property.value.id!!, property.value, selectedImages.value)
            }
            onLoadingState.value = false
            if (response.status == 200) {
                hiddenEditButton()
                selectedImages.value = emptyList()
                inputErrors.value = emptyMap()
                messageError.value = ""
                snackMessage.value.showSnackbar("Propriedade editada com sucesso!")
            } else {
                val errors = mutableMapOf<String, String>()
                response.errorFields?.forEach { errors[it.name] = it.description }
                inputErrors.value = errors
                messageError.value = response.message
            }
        }
    }

    fun createAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PropertyHttpClient(createHttpClient())
            val response = httpClient.create(property = property.value, selectedImages.value)
            onLoadingState.value = false
            if (response.status == 200) {
                property.value = Property()
                selectedImages.value = emptyList()
                inputErrors.value = emptyMap()
                messageError.value = ""
                snackMessage.value.showSnackbar("Propriedade salva com sucesso!")
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
            val httpClient = PropertyHttpClient(createHttpClient())
            var success = false
            if (property.value.id != null) {
                success = httpClient.delete(property.value.id!!)
            }
            showDialogState.value = false
            goToShowProperties()
            if (success) {
                snackMessage.value.showSnackbar("Propriedade deletada com sucesso!")
            } else {
                snackMessage.value.showSnackbar("Houve um problema ao deletar a propriedade!")
            }
        }
    }

    fun changeTitle(it: String) {
        property.value = property.value.copy(title = it)
    }
    fun changeRental(it: String) {
        property.value = property.value.copy(
            rentalValue = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                property.value.rentalValue
            }
        )
    }

    fun changeBedrooms(it: String) {
        property.value = property.value.copy(
            bedrooms = if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                property.value.bedrooms
            }
        )
    }

    fun changeArea(it: String) {
        property.value = property.value.copy(
            area = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                property.value.area
            }
        )
    }

    fun changeBathrooms(it: String) {
        property.value = property.value.copy(
            bathrooms = if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                property.value.bathrooms
            }
        )
    }

    fun changeGarageSpaces(it: String) {
        property.value = property.value.copy(
            garageSpaces = if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                property.value.garageSpaces
            }
        )
    }

    fun changeDescription(it: String) {
        property.value = property.value.copy(description = it)
    }

    fun changeCep(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(cep = it)
        )
    }

    fun changeState(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(state = it)
        )
    }

    fun changeCity(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(city = it)
        )
    }

    fun changeNeighborhood(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(neighborhood = it)
        )
    }

    fun changeStreet(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(street = it)
        )
    }

    fun changeNumber(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(
                number = if (it.toIntOrNull() != null || it == "") {
                    it
                } else {
                    property.value.address.number
                }
            )
        )
    }

    fun changeComplement(it: String) {
        property.value = property.value.copy(
            address = property.value.address.copy(complement = it)
        )
    }
}