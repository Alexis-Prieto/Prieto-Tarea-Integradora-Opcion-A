package com.prieto.clinicasalud

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSeleccion(
    doctorId: Int,
    onVolver: () -> Unit,
    onContinuar: (String, String) -> Unit
) {
    val doctor = SaludRepository.doctores.find { it.id == doctorId }

    var fechaSeleccionada by remember { mutableStateOf(doctor?.fechasDisponibles?.firstOrNull() ?: "Vie 27") }
    var horaSeleccionada by remember { mutableStateOf(doctor?.horasDisponibles?.firstOrNull() ?: "10:30") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita", fontWeight = FontWeight.Bold) },
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
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Selecciona fecha",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(doctor.fechasDisponibles) { fecha ->
                            val esSeleccionado = (fecha == fechaSeleccionada)
                            val parts = fecha.split(" ")

                            Surface(
                                onClick = { fechaSeleccionada = fecha },
                                shape = RoundedCornerShape(16.dp),
                                color = if (esSeleccionado) Color(0xFF532486) else Color(0xFFF3EDF7),
                                modifier = Modifier.size(width = 80.dp, height = 75.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    if (parts.size >= 2) {
                                        Text(
                                            text = parts[0],
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (esSeleccionado) Color.White else Color.DarkGray
                                        )
                                        Text(
                                            text = parts[1],
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if (esSeleccionado) Color.White else Color.Black
                                        )
                                    } else {
                                        Text(
                                            text = fecha,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (esSeleccionado) Color.White else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Selecciona hora",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(doctor.horasDisponibles) { hora ->
                            val esSeleccionado = (hora == horaSeleccionada)

                            Surface(
                                onClick = { horaSeleccionada = hora },
                                shape = RoundedCornerShape(16.dp),
                                color = if (esSeleccionado) Color(0xFF532486) else Color(0xFFF3EDF7),
                                modifier = Modifier.size(width = 90.dp, height = 48.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = hora,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (esSeleccionado) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        SaludRepository.citasReservadas.add(
                            0,
                            Cita(
                                id = SaludRepository.citasReservadas.size + 1,
                                doctorNombre = doctor.nombre,
                                especialidad = doctor.especialidad,
                                fecha = fechaSeleccionada,
                                hora = horaSeleccionada,
                                estado = "Confirmada"
                            )
                        )
                        onContinuar(doctor.nombre, "$fechaSeleccionada, $horaSeleccionada")
                    },
                    enabled = fechaSeleccionada.isNotEmpty() && horaSeleccionada.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF532486),
                        disabledContainerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Text("Confirmar cita", style = MaterialTheme.typography.titleMedium, color = Color.White)
                }
            }
        }
    }
}