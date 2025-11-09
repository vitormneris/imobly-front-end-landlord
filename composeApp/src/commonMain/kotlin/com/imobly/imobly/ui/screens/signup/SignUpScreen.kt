package com.imobly.imobly.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.clickable.ClickableComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputPasswordComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.LandLordViewModel
import imobly.composeapp.generated.resources.Res
import imobly.composeapp.generated.resources.image_logo_white
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SignUpScreen(landLordViewModel: LandLordViewModel) {
    val scrollState = rememberScrollState()

    landLordViewModel.whenStartingThePage()

    MaterialTheme {
        Scaffold(
            topBar = {},
            snackbarHost = {
                SnackbarHost(landLordViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .background(Color(242, 96, 63))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.image_logo_white),
                        contentDescription = "Logo do Imobly",
                        modifier = Modifier.size(160.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(paddingValues = PaddingValues(0.dp, 160.dp, 0.dp, 0.dp))
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(50.dp, 50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp, 50.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(40.dp))
                    TitleComp("Cadastrar", { landLordViewModel.goToLogin() }, true, 30.sp)
                    Spacer(Modifier.size(20.dp))

                    Column(
                        Modifier
                            .widthIn(max = 1000.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InputComp(
                            label = "Nome",
                            placeholder = "Ex: João",
                            value = landLordViewModel.landLord.value.firstName,
                            onValueChange = { landLordViewModel.changeFirstName(it) },
                            isError = landLordViewModel.inputContainsError("firstName"),
                            errorMessage = landLordViewModel.getInputErrorMessage("firstName")
                        )

                        InputComp(
                            label = "Sobrenome",
                            placeholder = "Ex: Silva",
                            value = landLordViewModel.landLord.value.lastName,
                            onValueChange = { landLordViewModel.changeLastName(it) },
                            isError = landLordViewModel.inputContainsError("lastName"),
                            errorMessage = landLordViewModel.getInputErrorMessage("lastName")
                        )

                        InputComp(
                            label = "E-mail",
                            placeholder = "Ex: joao@dominio.com",
                            value = landLordViewModel.landLord.value.email,
                            onValueChange = { landLordViewModel.changeEmail(it) },
                            isError = landLordViewModel.inputContainsError("email"),
                            errorMessage = landLordViewModel.getInputErrorMessage("email")
                        )

                        InputPasswordComp(
                            label = "Senha",
                            placeholder = "Ex: ********",
                            value = landLordViewModel.landLord.value.password,
                            onValueChange = { landLordViewModel.changePassword(it) },
                            isError = landLordViewModel.inputContainsError("password"),
                            errorMessage = landLordViewModel.getInputErrorMessage("password"),
                            passwordVisible = landLordViewModel.passwordVisibilityState.value,
                            changePasswordVisible = { landLordViewModel.changePasswordVisibility() }
                        )

                        InputComp(
                            label = "Telefone 1",
                            placeholder = "Ex: (00) 00000-0000",
                            value = landLordViewModel.landLord.value.telephones.telephone1,
                            onValueChange = { landLordViewModel.changeTelephoneOne(it) },
                            isError = (landLordViewModel.inputContainsError("telephones") ||
                                    landLordViewModel.inputContainsError("telephones.telephone1")),
                            errorMessage = if (landLordViewModel.inputContainsError("telephones")) {
                                landLordViewModel.getInputErrorMessage("telephones")
                            } else {
                                landLordViewModel.getInputErrorMessage("telephones.telephone1")
                            }
                        )

                        InputComp(
                            label = "Telefone 2",
                            placeholder = "Ex: (00) 00000-0000",
                            value = landLordViewModel.landLord.value.telephones.telephone2 ?: "",
                            onValueChange = { landLordViewModel.changeTelephoneTwo(it) },
                            isError = landLordViewModel.inputContainsError("telephones.telephone2"),
                            errorMessage = landLordViewModel.getInputErrorMessage("telephones.telephone2")
                        )

                        InputComp(
                            label = "Telefone 3",
                            placeholder = "Ex: (00) 00000-0000",
                            value = landLordViewModel.landLord.value.telephones.telephone3 ?: "",
                            onValueChange = { landLordViewModel.changeTelephoneThree(it) },
                            isError = landLordViewModel.inputContainsError("telephones.telephone3"),
                            errorMessage = landLordViewModel.getInputErrorMessage("telephones.telephone3")
                        )
                    }

                    if (landLordViewModel.messageError.value != "") {
                        MessageErrorComp(landLordViewModel.messageError.value, 16.sp)
                    }

                    ButtonComp(
                        "Cadastrar-se",
                        { Icon(Icons.Default.PersonAdd, "check") },
                        PrimaryColor,
                        { landLordViewModel.signUpAction() }
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize().padding(vertical = 50.dp)
                    ) {

                        Text(
                            "Já possui conta? ",
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "Logue-se",
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { landLordViewModel.goToLogin() }
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    val navControllerFake = rememberNavController()
    SignUpScreen(LandLordViewModel(navControllerFake))
}
