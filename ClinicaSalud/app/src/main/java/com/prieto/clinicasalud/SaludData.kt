package com.prieto.clinicasalud

import androidx.compose.runtime.mutableStateListOf

data class Doctor(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val experiencia: String,
    val calificacion: Double,
    val resenas: Int,
    val descripcion: String,
    val fechasDisponibles: List<String> = listOf("Jue 26", "Vie 27", "Sáb 28"),
    val horasDisponibles: List<String> = listOf("9:00", "10:30", "3:00")
)

data class Cita(
    val id: Int,
    val doctorNombre: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String
)

object SaludRepository {
    val doctores = listOf(
        Doctor(
            id = 1,
            nombre = "Dra. Ana Torres",
            especialidad = "Cardiología",
            experiencia = "12 años exp.",
            calificacion = 4.9,
            resenas = 128,
            descripcion = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
        ),
        Doctor(
            id = 2,
            nombre = "Dr. Luis Vega",
            especialidad = "Pediatría",
            experiencia = "8 años exp.",
            calificacion = 4.7,
            resenas = 95,
            descripcion = "Atención integral infantil y desarrollo pediátrico."
        ),
        Doctor(
            id = 3,
            nombre = "Dra. Rosa Díaz",
            especialidad = "Dermatología",
            experiencia = "10 años exp.",
            calificacion = 4.8,
            resenas = 110,
            descripcion = "Dermatología clínica, médica y cuidado de la piel."
        )
    )

    val citasReservadas = mutableStateListOf(
        Cita(1, "Dra. Ana Torres", "Cardiología", "Viernes 27", "10:30 am", "Confirmada"),
        Cita(2, "Dr. Luis Vega", "Pediatría", "Miércoles 15", "3:00 pm", "Completada")
    )
}