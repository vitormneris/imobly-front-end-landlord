package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

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
}