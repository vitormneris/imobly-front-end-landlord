package com.imobly.imobly.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class TenantViewModel(val navController: NavHostController): ViewModel() {
    val searchText: MutableState<String> = mutableStateOf("")

    fun changeSearchText(it: String) {
        searchText.value =
            if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                searchText.value
            }
    }

    fun goToHome() {
        navController.navigate("home")
    }

}