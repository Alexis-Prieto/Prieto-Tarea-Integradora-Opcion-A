package com.prieto.clinicasalud

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSeleccion(
    doctorId: Int,
    onVolver: () -> Unit,
    onContinuar: (String, String) -> Unit
) {
    val doctor = SaludRepository.doctores.find { it.id == doctorId }

    var fechaSeleccionada by remember { mutableStateOf(doctor?.fechasDisponibles?.firstOrNull() ?: "") }
    var horaSeleccionada by remember { mutableStateOf(doctor?.horasDisponibles?.firstOrNull() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
                Column {
                    Text(
                        text = "Reserva con ${doctor.nombre}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = doctor.especialidad,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Selecciona un día", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(doctor.fechasDisponibles) { fecha ->
                            FilterChip(
                                selected = (fecha == fechaSeleccionada),
                                onClick = { fechaSeleccionada = fecha },
                                label = { Text(fecha) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Selecciona una hora", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(doctor.horasDisponibles) { hora ->
                            FilterChip(
                                selected = (hora == horaSeleccionada),
                                onClick = { horaSeleccionada = hora },
                                label = { Text(hora) }
                            )
                        }
                    }
                }
                Button(
                    onClick = { onContinuar(fechaSeleccionada, horaSeleccionada) },
                    enabled = fechaSeleccionada.isNotEmpty() && horaSeleccionada.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar cita")
                }
            }
        }
    }
}