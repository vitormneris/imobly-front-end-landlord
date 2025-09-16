package com.imobly.imobly

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.home.HomeScreen
import com.imobly.imobly.ui.screens.show.showproperty.ShowPropertyScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("showproperty") {
                ShowPropertyScreen(navController)
            }

            composable("home") {
                HomeScreen(navController)
            }
        }
    }
}
