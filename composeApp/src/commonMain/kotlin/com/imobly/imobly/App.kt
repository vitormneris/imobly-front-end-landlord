package com.imobly.imobly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.changepassword.ChangePasswordScreen
import com.imobly.imobly.ui.screens.create.createcategory.CreateCategoryScreen
import com.imobly.imobly.ui.screens.create.createlease.CreateLeaseScreen
import com.imobly.imobly.ui.screens.create.createproperty.CreatePropertyScreen
import com.imobly.imobly.ui.screens.edit.editcategory.EditCategoryScreen
import com.imobly.imobly.ui.screens.edit.editlandlord.EditLandLordScreen
import com.imobly.imobly.ui.screens.edit.editlease.EditLeaseScreen
import com.imobly.imobly.ui.screens.edit.editproperty.EditPropertyScreen
import com.imobly.imobly.ui.screens.edit.editreport.EditReportScreen
import com.imobly.imobly.ui.screens.edit.edittenant.EditTenantScreen
import com.imobly.imobly.ui.screens.forgotpassword.ForgotPasswordScreen
import com.imobly.imobly.ui.screens.home.HomeScreen
import com.imobly.imobly.ui.screens.insertcode.InsertCodeScreen
import com.imobly.imobly.ui.screens.login.LoginScreen
import com.imobly.imobly.ui.screens.show.showlease.ShowLeasesScreen
import com.imobly.imobly.ui.screens.show.showproperty.ShowPropertiesScreen
import com.imobly.imobly.ui.screens.show.showreports.ShowReportsScreen
import com.imobly.imobly.ui.screens.show.showtenant.ShowTenantScreen
import com.imobly.imobly.ui.screens.signup.SignUpScreen
import com.imobly.imobly.viewmodel.*

@Composable
fun App() {
    val navController = rememberNavController()
    val propertyViewModel = viewModel { PropertyViewModel(navController) }
    val tenantViewModel = viewModel { TenantViewModel(navController) }
    val landLordViewModel = viewModel { LandLordViewModel(navController) }
    val loginViewModel = viewModel { LoginViewModel(navController) }
    val reportViewModel = viewModel { ReportViewModel(navController) }
    val categoryViewModel = viewModel { CategoryViewModel(navController) }
    val leaseViewModel = viewModel { LeaseViewModel(navController) }
    val resetPasswordViewModel = viewModel { ResetPasswordViewModel(navController) }
    val homeViewModel = viewModel { HomeViewModel(navController) }


    MaterialTheme {
        NavHost(navController = navController, startDestination = "login") {

            composable(route = "home") {
                HomeScreen(homeViewModel)
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

            composable(route = "login") {
                LoginScreen(loginViewModel)
            }

            composable(route = "signup") {
                SignUpScreen(landLordViewModel)
            }

            composable(route = "showreports") {
                ShowReportsScreen(reportViewModel)
            }

            composable(route = "editreport") {
                EditReportScreen(reportViewModel)
            }

            composable(route = "editlandlord") {
                EditLandLordScreen(landLordViewModel)
            }

            composable(route = "forgotpassword") {
                ForgotPasswordScreen(resetPasswordViewModel)
            }

            composable(route = "insertcode") {
                InsertCodeScreen(resetPasswordViewModel)
            }

            composable(route = "changepassword") {
                ChangePasswordScreen(resetPasswordViewModel)
            }

            composable(route = "editcategory") {
                EditCategoryScreen(categoryViewModel)
            }

            composable(route = "createcategory") {
                CreateCategoryScreen(categoryViewModel)
            }

            composable(route = "showleases") {
                ShowLeasesScreen(leaseViewModel)
            }

            composable(route = "editlease") {
                EditLeaseScreen(leaseViewModel)
            }

            composable(route = "createlease") {
                CreateLeaseScreen(leaseViewModel)
            }
        }
    }
}