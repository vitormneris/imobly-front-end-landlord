package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.LeaseDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.LeaseHttpClient
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.api.httpclient.TenantHttpClient
import com.imobly.imobly.domain.Lease
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Tenant
import kotlinx.coroutines.launch

class LeaseViewModel(private val navController: NavHostController): ViewModel() {
    val lease = mutableStateOf(Lease())
    val leases = mutableStateOf<List<Lease>>(emptyList())

    val properties: MutableState<List<Property>> = mutableStateOf(emptyList())
    val propertySelected = mutableStateOf(Property())
    val tenants: MutableState<List<Tenant>> = mutableStateOf(emptyList())
    val tenantSelected = mutableStateOf(Tenant())

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val showDialogState = mutableStateOf(false)
    val onLoadingState = mutableStateOf(false)

    val searchText: MutableState<String> = mutableStateOf("")

    val inputLockState = mutableStateOf(true)

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val leaseHttpClient = LeaseHttpClient(createHttpClient())
            val tenantHttpClient = TenantHttpClient(createHttpClient())
            val propertyHttpClient = PropertyHttpClient(createHttpClient())

            val leasesFound = leaseHttpClient.searchAllByTitleOrName()
            leases.value = leasesFound

            val tenantsFound = tenantHttpClient.searchAllByNameOrCpf()
            tenants.value = tenantsFound

            val propertiesFound = propertyHttpClient.searchAllByTitle()
            properties.value = propertiesFound
        }
    }

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = LeaseHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitleOrName(searchText.value)
            leases.value = list
        }
    }

    fun createAction() {
        lease.value.property = propertySelected.value
        lease.value.tenant = tenantSelected.value

        val dto = LeaseDTO(
            lease.value.startDate,
            lease.value.endDate,
            lease.value.property.id ?: "",
            lease.value.tenant.id ?: "",
            lease.value.monthlyRent,
            lease.value.securityDeposit,
            lease.value.paymentDueDay
        )

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = LeaseHttpClient(createHttpClient())
            val response = httpClient.create(dto)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    lease.value = Lease()
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    snackMessage.value.showSnackbar("Contrato cadastrado com sucesso!")
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
            val httpClient = LeaseHttpClient(createHttpClient())
            if (lease.value.id != null) {
                val response = httpClient.update(lease.value.id!!, lease.value)
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        snackMessage.value.showSnackbar("Locação editado com sucesso!")
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

    fun toggleEnableAction() {
        viewModelScope.launch {
            val httpClient = LeaseHttpClient(createHttpClient())
            if (lease.value.id != null) {
                val response = httpClient.toggleEnable(lease.value.id!!)
                showDialogState.value = false
                when (response) {
                    is Ok -> {
                        if (lease.value.isEnabled) {
                            snackMessage.value.showSnackbar("Contrato desativado com sucesso!")
                        } else {
                            snackMessage.value.showSnackbar("Contrato ativado com sucesso!")
                        }
                        lease.value = httpClient.findById(lease.value.id!!)
                    }

                    is ErrorDTO -> {
                        if (lease.value.isEnabled) {
                            snackMessage.value.showSnackbar("Houve um problema ao desativar a contrato!")
                        } else {
                            snackMessage.value.showSnackbar("Houve um problema ao ativar a contrato!")
                        }
                    }
                }
            }
        }
    }

    fun changeMonthlyRent(it: String) {
        lease.value = lease.value.copy(
            monthlyRent = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                lease.value.monthlyRent
            }
        )
    }

    fun changeSecurityDeposit(it: String) {
        lease.value = lease.value.copy(
            securityDeposit = if (it.toDoubleOrNull() != null || it == "") {
                it
            } else {
                lease.value.securityDeposit
            }
        )
    }

    fun changePaymentDueDay(it: String) {
        lease.value = lease.value.copy(
            paymentDueDay = if ((it.toIntOrNull() != null && 0 < it.toInt() && it.toInt() <= 31) || it == "") {
                it
            } else {
                lease.value.paymentDueDay
            }
        )
    }

    fun changeStartDate(it: String) {
        lease.value = lease.value.copy(startDate = it)
    }

    fun changeEndDate(it: String) {
        lease.value = lease.value.copy(endDate = it)
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }
    fun resetPage() {
        lease.value = Lease()
        inputErrors.value = emptyMap()
        messageError.value = ""
        tenantSelected.value = Tenant()
        propertySelected.value = Property()
    }

    fun propertiesOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        properties.value.forEach {
            map[it.title] = it.id ?: ""
        }
        return map
    }

    fun tenantsOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        tenants.value.forEach {
            map["${it.firstName} ${it.lastName}"] = it.id ?: ""
        }
        return map
    }

    fun onOptionSelectedTenant(id: String){
        tenantSelected.value = tenants.value.first { it.id == id }
    }

    fun onOptionSelectedProperty(id: String){
        propertySelected.value = properties.value.first { it.id == id }
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToCreateLease() {
        navController.navigate("createlease")
    }

    fun goToEditLease(newLease: Lease) {
        lease.value = newLease
        inputLockState.value = true
        inputErrors.value = emptyMap()
        messageError.value = ""
        navController.navigate("editlease")
    }
    fun goToShowLeases() {
        navController.navigate("showleases")
    }
}