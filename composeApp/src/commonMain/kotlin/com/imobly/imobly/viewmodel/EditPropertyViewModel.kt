package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.model.Property
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult

class EditPropertyViewModel: ViewModel() {
    val property = mutableStateOf(Property())
    val selectedImages = mutableStateOf(emptyList<GalleryPhotoResult>())

    fun topBarAction(navController: NavHostController) {
        navController.navigate("home")
    }

    fun editAction() {
        // TODO
    }

    fun changeTitle(it: String) {
        property.value = property.value.copy(title = it)
    }
    fun changeRental(it: String) {
        property.value = property.value.copy(
            rentValue = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                property.value.rentValue
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