package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.httpclient.CategoryHttpClient
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Category
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class PropertyViewModel(private val navController: NavHostController): ViewModel() {
    val property = mutableStateOf(Property())

    val categories: MutableState<List<Category>> = mutableStateOf(emptyList())

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

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
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
            val categoryHttpClient = CategoryHttpClient(createHttpClient())
            categories.value = categoryHttpClient.searchAll()
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

    fun createAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PropertyHttpClient(createHttpClient())
            val response = httpClient.create(property = property.value, selectedImages.value)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    property.value = Property()
                    selectedImages.value = emptyList()
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    snackMessage.value.showSnackbar("Propriedade salva com sucesso!")
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
            val httpClient = PropertyHttpClient(createHttpClient())
            if (property.value.id != null) {
                val response = httpClient.update(property.value.id!!, property.value, selectedImages.value)

                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        selectedImages.value = emptyList()
                        inputErrors.value = emptyMap()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Propriedade editada com sucesso!")
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
    }

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            if (property.value.id != null) {
                val response = httpClient.delete(property.value.id!!)
                showDialogState.value = false
                goToShowProperties()
                when (response) {
                    is Ok -> {
                        snackMessage.value.showSnackbar("Propriedade deletada com sucesso!")
                    }

                    is ErrorDTO -> {
                        snackMessage.value.showSnackbar("Houve um problema ao deletar a propriedade!")
                    }
                }
            }
        }
    }

    fun changeTitle(it: String) {
        property.value = property.value.copy(title = it)
    }
    fun changeMonthlyRent(it: String) {
        property.value = property.value.copy(
            monthlyRent = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                property.value.monthlyRent
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

    fun changeCategory(category: Category) {
        property.value = property.value.copy(category = category)
    }

}