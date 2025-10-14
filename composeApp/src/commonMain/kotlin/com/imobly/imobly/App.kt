package com.imobly.imobly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.create.createproperty.CreatePropertyScreen
import com.imobly.imobly.ui.screens.edit.editproperty.EditPropertyScreen
import com.imobly.imobly.ui.screens.home.HomeScreen

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable(route = "editproperty") {
                EditPropertyScreen(navController)
            }

            composable(route = "home") {
                HomeScreen(navController)
            }

            composable(route = "createproperty") {
                CreatePropertyScreen(navController)
            }
        }
    }
}