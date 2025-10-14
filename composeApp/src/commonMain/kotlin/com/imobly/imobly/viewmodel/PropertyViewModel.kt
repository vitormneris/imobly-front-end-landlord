package com.imobly.imobly.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.property.PropertyHttpClient
import com.imobly.imobly.domain.Property
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch

class PropertyViewModel(val navController: NavHostController): ViewModel() {
    val property = mutableStateOf(Property())
    val selectedImages = mutableStateOf(emptyList<GalleryPhotoResult>())

    val searchText: MutableState<String> = mutableStateOf("")
    val properties: MutableState<List<Property>> = mutableStateOf(emptyList())

    fun changeSearchText(it: String) {
        searchText.value =
            if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                searchText.value
            }
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            properties.value = httpClient.searchAll()
        }
    }

    fun goToEditProperty(propertyNew: Property) {
        property.value = propertyNew
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
            val httpClient = PropertyHttpClient(createHttpClient())
            if (property.value.id != null) {
                httpClient.update(property.value.id!!, property.value)
            }
            goToShowProperties()
        }
    }

    fun createAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            httpClient.create(property = property.value)
            property.value = Property()
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