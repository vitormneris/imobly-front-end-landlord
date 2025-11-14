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
import com.imobly.imobly.api.httpclient.CategoryHttpClient
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.api.httpclient.TenantHttpClient
import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.Property
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val navController: NavHostController) : ViewModel() {

    var category = mutableStateOf(Category())

    var categories = mutableStateOf<List<Category>>(emptyList())
    var properties = mutableStateOf(mutableListOf<String>())
    var propertyText = mutableStateOf("")

    var onLoadingState = mutableStateOf(false)
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")

    var snackMessage = mutableStateOf(SnackbarHostState())

    var inputLockState = mutableStateOf(true)
    var showDialogState = mutableStateOf(false)

    val searchText: MutableState<String> = mutableStateOf("")

    fun resetPage() {
        messageError.value = ""
        propertyText.value = ""
        properties.value.clear()
        category.value = Category()
        inputLockState.value = true
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
        messageError.value = ""
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun changeTitle(title: String) {
        category.value = category.value.copy(title = title)
    }

    fun changePropertyText(text: String) {
        propertyText.value = text
    }

    fun removeProperty(index: Int) {
        val list = properties.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            properties.value = list
        }
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = CategoryHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitle(searchText.value)
            categories.value = list
        }
    }


    fun createAction() {
         viewModelScope.launch {
             onLoadingState.value = true
             val httpClient = CategoryHttpClient(createHttpClient())
             val response = httpClient.create(category.value)
             onLoadingState.value = false
             when (response) {
                 is Ok -> {
                     category.value = Category()
                     inputErrors.value = emptyMap()
                     messageError.value = ""
                     snackMessage.value.showSnackbar("Categoria salva com sucesso!")
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
            val httpClient = CategoryHttpClient(createHttpClient())
            if (category.value.id != null) {
                val response = httpClient.update(category.value.id!!, category.value)
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        snackMessage.value.showSnackbar("Locatário editado com sucesso!")
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
              val httpClient = TenantHttpClient(createHttpClient())
              if (category.value.id != null) {
                  val response = httpClient.delete(category.value.id!!)

                  showDialogState.value = false
                  goToShowCategories()
                  when (response) {
                      is Ok -> {
                          snackMessage.value.showSnackbar("Categoria deletada com sucesso!")
                      }

                      is ErrorDTO -> {
                          snackMessage.value.showSnackbar("Houve um problema ao deletar a categoria!")
                      }
                  }
              }
          }
    }

    fun goToShowCategories() {
        navController.navigate("showcategories")
    }

    fun goToHome() {
        navController.navigate("home")
    }
}