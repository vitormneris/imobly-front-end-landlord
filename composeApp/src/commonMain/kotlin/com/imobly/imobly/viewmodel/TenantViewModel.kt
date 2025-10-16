package com.imobly.imobly.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Tenant

class TenantViewModel(val navController: NavHostController): ViewModel() {

    val tenants: MutableState<List<Tenant>> = mutableStateOf(emptyList())

    val tenant = mutableStateOf(Tenant())
    val searchText: MutableState<String> = mutableStateOf("")

    fun changeSearchText(it: String) {
        searchText.value =
            if (it.toIntOrNull() != null || it == "") {
                it
            } else {
                searchText.value
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

}