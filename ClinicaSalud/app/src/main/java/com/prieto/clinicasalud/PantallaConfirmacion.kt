package com.prieto.clinicasalud

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConfirmacion(
    doctorId: Int,
    fecha: String,
    hora: String,
    onVolver: () -> Unit,
    onConfirmar: () -> Unit
) {
    val doctor = SaludRepository.doctores.find { it.id == doctorId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmar Cita") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        if (doctor != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Resumen de la Reserva", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Médico: ${doctor.nombre}", style = MaterialTheme.typography.titleMedium)
                            Text("Especialidad: ${doctor.especialidad}", style = MaterialTheme.typography.bodyMedium)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                            Text("Fecha: $fecha", style = MaterialTheme.typography.bodyLarge)
                            Text("Hora: $hora", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                Button(
                    onClick = {
                        SaludRepository.citasReservadas.add(
                            Cita(
                                id = SaludRepository.citasReservadas.size + 1,
                                doctorNombre = doctor.nombre,
                                especialidad = doctor.especialidad,
                                fecha = fecha,
                                hora = hora,
                                estado = "Confirmada"
                            )
                        )
                        onConfirmar()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver mis citas")
                }
            }
        }
    }
}