package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.Property
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val nav: NavHostController? = null) : ViewModel() {

    var category = mutableStateOf(Category())
    var properties = mutableStateOf(mutableListOf<String>())
    var propertyText = mutableStateOf("")

    var onLoadingState = mutableStateOf(false)
    var messageError = mutableStateOf("")
    var snackMessage = mutableStateOf(SnackbarHostState())

    var errors = mutableMapOf<String, String>()

    // ===== Estados para EDIÇÃO =====
    var inputLockState = mutableStateOf(true) // true = somente visualizar
    var showDialogState = mutableStateOf(false) // modal de excluir

    fun resetPage() {
        messageError.value = ""
        errors.clear()
        propertyText.value = ""
        properties.value.clear()
        category.value = Category()
        inputLockState.value = true
    }

    fun hiddenEditButton() {
        // troca entre somente visualizar <-> editar
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

    fun addProperty() {
        if (propertyText.value.isNotBlank()) {
            properties.value = (properties.value + propertyText.value).toMutableList()
            propertyText.value = ""
        }
    }

    fun removeProperty(index: Int) {
        val list = properties.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            properties.value = list
        }
    }

    fun inputContainsError(key: String) = errors.containsKey(key)
    fun getInputErrorMessage(key: String) = errors[key] ?: ""

    private fun validate(): Boolean {
        errors.clear()

        if (category.value.title.isBlank())
            errors["title"] = "Informe o título da categoria"

        if (properties.value.isEmpty())
            errors["properties"] = "Adicione pelo menos uma propriedade"

        return errors.isEmpty()
    }

    // ===================================================================================
    // CREATE
    // ===================================================================================
    fun createAction() {
        if (!validate()) return
        onLoadingState.value = true

        MainScope().launch {
            try {
                val props = properties.value.map { Property(title = it) }
                category.value = category.value.copy(properties = props)

                // TODO: chamada API

                goToShowCategories()

            } catch (e: Exception) {
                messageError.value = e.message ?: "Erro inesperado"
            } finally {
                onLoadingState.value = false
            }
        }
    }

    // ===================================================================================
    // UPDATE
    // ===================================================================================

    fun updateAction() {
        if (!validate()) return
        onLoadingState.value = true

        MainScope().launch {
            try {
                val props = properties.value.map { Property(title = it) }
                category.value = category.value.copy(properties = props)

                // TODO: chamada API para atualizar categoria

                hiddenEditButton() // fecha edição
                goToShowCategories()

            } catch (e: Exception) {
                messageError.value = e.message ?: "Erro ao atualizar categoria"
            } finally {
                onLoadingState.value = false
            }
        }
    }

    // ===================================================================================
    // DELETE
    // ===================================================================================
    fun deleteAction() {
        onLoadingState.value = true

        MainScope().launch {
            try {
                // TODO: chamada API para excluir categoria

                goToShowCategories()

            } catch (e: Exception) {
                messageError.value = e.message ?: "Erro ao excluir"
            } finally {
                onLoadingState.value = false
            }
        }
    }

    fun goToShowCategories() {
        nav?.navigate("showCategories")
    }
}