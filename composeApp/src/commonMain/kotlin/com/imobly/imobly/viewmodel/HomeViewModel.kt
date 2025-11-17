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
import com.imobly.imobly.api.httpclient.GraphicHttpClient
import com.imobly.imobly.domain.Auth
import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.graph.RentByMonth
import com.imobly.imobly.domain.graph.RentedProperties
import com.imobly.imobly.domain.graph.RentsPaidThisMonth
import kotlinx.coroutines.launch

class HomeViewModel(private val navController: NavHostController): ViewModel() {
    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun goToShowProperties() {
        navController.navigate("showproperties")
    }

    fun goToShowTenants() {
        navController.navigate("showtenants")
    }

    fun goToShowReports() {
        navController.navigate("showreports")
    }

    fun goToCreateCategory() {
        navController.navigate("createcategory")
    }

    fun goToShowLeases() {
        navController.navigate("showleases")
    }

    fun goToEditLandLord() {
        navController.navigate("editlandlord")
    }

    var rentByMonth = mutableStateOf(RentByMonth())

    var rentedProperties = mutableStateOf(RentedProperties())

    var rentsPaidThisMonth = mutableStateOf(RentsPaidThisMonth())

    fun loadGraphics() {
        loadRentByMonth()
        loadRentedProperties()
        loadRentsPaidThisMonth()
    }

    fun loadRentByMonth() {
        viewModelScope.launch {
            val httpClient = GraphicHttpClient(createHttpClient())
            val list = httpClient.getChartRentByMonth()
            rentByMonth.value = list
        }
    }

    fun loadRentedProperties() {
        viewModelScope.launch {
            val httpClient = GraphicHttpClient(createHttpClient())
            val list = httpClient.getChartRentedProperties()
            rentedProperties.value = list
        }
    }

    fun loadRentsPaidThisMonth() {
        viewModelScope.launch {
            val httpClient = GraphicHttpClient(createHttpClient())
            val list = httpClient.getChartRentsPaidThisMonth()
            rentsPaidThisMonth.value = list
        }
    }
}