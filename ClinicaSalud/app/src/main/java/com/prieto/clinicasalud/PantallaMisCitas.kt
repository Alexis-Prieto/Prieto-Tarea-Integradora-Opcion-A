package com.prieto.clinicasalud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMisCitas(
    onAbrirDrawer: () -> Unit
) {
    val citas = SaludRepository.citasReservadas

    Scaffold(
        containerColor = Color.White, // <--- 1. FONDO BLANCO PURO DE LA PANTALLA
        topBar = {
            TopAppBar(
                title = { Text("Mis citas", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onAbrirDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White // <--- 2. FONDO BLANCO PURO DE LA BARRA
                )
            )
        }
    ) { padding ->
        if (citas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes citas agendadas.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(citas) { cita ->
                    val esConfirmada = cita.estado.equals("Confirmada", ignoreCase = true)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            // Indicador lateral morado para citas confirmadas
                            if (esConfirmada) {
                                Box(
                                    modifier = Modifier
                                        .width(5.dp)
                                        .fillMaxHeight()
                                        .background(Color(0xFF532486))
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Nombre del Doctor
                                Text(
                                    text = cita.doctorNombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                // Fecha y Hora juntas en gris
                                Text(
                                    text = "${cita.fecha}, ${cita.hora}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Etiqueta de Estado en la parte inferior
                                Surface(
                                    color = if (esConfirmada) Color(0xFFE8F8EE) else Color(0xFFE5E5E5),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = cita.estado,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (esConfirmada) Color(0xFF00A86B) else Color(0xFF666666),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}