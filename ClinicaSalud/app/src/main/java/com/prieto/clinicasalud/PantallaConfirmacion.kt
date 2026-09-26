package com.prieto.clinicasalud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PantallaConfirmacion(
    doctorId: Int,
    fecha: String,
    hora: String,
    onVolver: () -> Unit,
    onConfirmar: () -> Unit
) {
    val doctor = SaludRepository.doctores.find { it.id == doctorId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // <--- FONDO BLANCO PURO
            .navigationBarsPadding()  // Evita superposición con la barra del sistema
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Círculo verde claro con el ícono de check
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFFE8F8EE), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF00A86B),
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título principal
        Text(
            text = "¡Cita agendada!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre del doctor
        Text(
            text = doctor?.nombre ?: "Doctor",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        // Fecha y hora
        Text(
            text = "$fecha, $hora",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón "Ver mis citas"
        Button(
            onClick = onConfirmar,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF3EDF7),
                contentColor = Color.Black
            ),
            modifier = Modifier.height(44.dp)
        ) {
            Text(
                text = "Ver mis citas",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}