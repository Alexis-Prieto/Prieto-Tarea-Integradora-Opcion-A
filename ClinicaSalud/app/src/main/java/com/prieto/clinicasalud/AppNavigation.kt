package com.prieto.clinicasalud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Detecta la ruta activa para iluminar la opción e ícono correspondiente
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.width(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header del Paciente (Avatar con Iniciales)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFF3EDF7), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AP",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF532486),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Alexis Prieto",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Paciente",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    HorizontalDivider(
                        color = Color(0xFFEEEEEE),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Lista de opciones del Drawer
                    val opciones = listOf(
                        "Inicio" to "inicio",
                        "Mis citas" to "mis_citas",
                        "Historial médico" to "historial",
                        "Perfil" to "perfil"
                    )

                    opciones.forEach { (label, route) ->
                        val seleccionado = currentRoute == route

                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = if (seleccionado) {
                                        Icons.Filled.RadioButtonChecked
                                    } else {
                                        Icons.Outlined.RadioButtonUnchecked
                                    },
                                    contentDescription = null,
                                    tint = if (seleccionado) Color(0xFF532486) else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                                    color = if (seleccionado) Color(0xFF532486) else Color.Black
                                )
                            },
                            selected = seleccionado,
                            onClick = {
                                scope.launch { drawerState.close() }

                                when (route) {
                                    "inicio" -> {
                                        if (currentRoute != "inicio") {
                                            navController.navigate("inicio") {
                                                popUpTo("inicio") { inclusive = true }
                                            }
                                        }
                                    }
                                    "mis_citas" -> {
                                        if (currentRoute != "mis_citas") {
                                            navController.navigate("mis_citas") {
                                                popUpTo("inicio")
                                            }
                                        }
                                    }
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color(0xFFF3EDF7),
                                unselectedContainerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") {
                PantallaInicio(
                    onAbrirDrawer = { scope.launch { drawerState.open() } },
                    onDoctorClick = { doctorId ->
                        navController.navigate("detalle/$doctorId")
                    }
                )
            }
            composable("detalle/{doctorId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("doctorId")?.toIntOrNull() ?: 1
                PantallaDetalle(
                    doctorId = id,
                    onVolver = { navController.popBackStack() },
                    onReservarClick = { doctorId ->
                        navController.navigate("seleccion/$doctorId")
                    }
                )
            }
            composable("seleccion/{doctorId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("doctorId")?.toIntOrNull() ?: 1
                PantallaSeleccion(
                    doctorId = id,
                    onVolver = { navController.popBackStack() },
                    onContinuar = { fecha, hora ->
                        navController.navigate("confirmacion/$id/$fecha/$hora")
                    }
                )
            }
            composable("confirmacion/{doctorId}/{fecha}/{hora}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("doctorId")?.toIntOrNull() ?: 1
                val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
                val hora = backStackEntry.arguments?.getString("hora") ?: ""

                PantallaConfirmacion(
                    doctorId = id,
                    fecha = fecha,
                    hora = hora,
                    onVolver = { navController.popBackStack() },
                    onConfirmar = {
                        navController.navigate("mis_citas") {
                            popUpTo("inicio")
                        }
                    }
                )
            }
            composable("mis_citas") {
                PantallaMisCitas(
                    onAbrirDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
    }
}