package com.imobly.imobly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.create.createTenant.CreateTenantScreen
import com.imobly.imobly.ui.screens.create.createproperty.CreatePropertyScreen
import com.imobly.imobly.ui.screens.edit.editproperty.EditPropertyScreen
import com.imobly.imobly.ui.screens.edit.edittenant.EditTenantScreen
import com.imobly.imobly.ui.screens.home.HomeScreen
import com.imobly.imobly.ui.screens.show.showproperty.ShowPropertiesScreen
import com.imobly.imobly.ui.screens.show.showreports.ShowReportsScreen
import com.imobly.imobly.ui.screens.show.showtenant.ShowTenantScreen
import com.imobly.imobly.viewmodel.PropertyViewModel
import com.imobly.imobly.viewmodel.TenantViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    val propertyViewModel = viewModel { PropertyViewModel(navController) }
    val tenantViewModel = viewModel { TenantViewModel(navController) }

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable(route = "home") {
                HomeScreen(navController)
            }

            composable(route = "showproperties") {
                ShowPropertiesScreen(propertyViewModel)
            }

            composable(route = "editproperty") {
                EditPropertyScreen(propertyViewModel)
            }

            composable(route = "createproperty") {
                CreatePropertyScreen(propertyViewModel)
            }

            composable(route = "showtenants") {
                ShowTenantScreen(tenantViewModel)
            }

            composable(route = "edittenant") {
                EditTenantScreen(tenantViewModel)
            }

            composable(route = "createtenant") {
                CreateTenantScreen(tenantViewModel)
            }

            composable(route = "showreports") {
                ShowReportsScreen(navController)
            }
        }
    }
}