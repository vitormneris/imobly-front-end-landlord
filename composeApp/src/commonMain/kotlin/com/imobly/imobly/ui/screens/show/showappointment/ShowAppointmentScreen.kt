package com.imobly.imobly.ui.screens.show.showappointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Appointment
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.AppointmentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowAppointmentsScreen(appointmentViewModel: AppointmentViewModel) {
    appointmentViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(appointmentViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars,
    ) { paddingValues ->
        Column(
            modifier = Modifier.background(BackGroundColor).padding(paddingValues).fillMaxSize()
        ) {
            TitleComp(text = "Agendamentos", buttonBackAction = { appointmentViewModel.goToHome() })

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                SearchBarComp(
                    "Buscar agendamento pelo título do imóvel",
                    appointmentViewModel.searchText.value,
                    { appointmentViewModel.changeSearchText(it) },
                    { appointmentViewModel.searchAction() })
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(appointmentViewModel.appointments.value) { appointment ->
                    ShowAppointmentCard(
                        appointment = appointment,
                        appointmentViewModel = appointmentViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun ShowAppointmentCard(appointment: Appointment, appointmentViewModel: AppointmentViewModel) {
    Card(
        modifier = Modifier.padding(10.dp).widthIn(max = 700.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextInfoComp("Imóvel", appointment.property.title)
            TextInfoComp("Nome do locador", appointment.guideName)
            TextInfoComp("Nome do visitante", appointment.guestName)
            TextInfoComp("Telefones", appointment.telephone)
            TextInfoComp("Título da propriedade", appointment.property.title)
            TextInfoComp(
                "Endereço",
                "${appointment.property.address.neighborhood}, ${appointment.property.address.street} Nº ${appointment.property.address.number}, ${appointment.property.address.city}/${appointment.property.address.state}"
            )

            ConfirmDialogComp(
                appointmentViewModel.showDialogState.value,
                "Está ação deletará este relato",
                "Tem certeza que deseja continuar? Está ação é irreversível.",
                { appointmentViewModel.deleteAction(appointment) },
                { appointmentViewModel.changeShowDialog() }
            )

            Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                ButtonComp(
                    "Excluir",
                    { Icon(Icons.Default.Delete, "deletar") },
                    CancelColor,
                    { appointmentViewModel.changeShowDialog() },
                    140.dp,
                    16.sp
                )
            }
        }
    }
}

@Composable
fun TextInfoComp(label: String, value: String, color: Color = Color.Black) {
    Row(Modifier.padding(2.dp)) {
        Text(
            "${label}:  ",
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = montserratFont(),
            fontWeight = FontWeight.SemiBold
        )

        Text(
            value,
            color = color,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = montserratFont(),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun ShowAppointmentsPreview() {
    val navControllerFake = rememberNavController()
    ShowAppointmentsScreen(AppointmentViewModel(navControllerFake))
}
