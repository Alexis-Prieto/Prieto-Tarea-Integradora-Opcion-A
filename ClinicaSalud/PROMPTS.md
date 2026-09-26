# Flujo de Prompts para la Construcción de la Aplicación — Clínica Salud

## Prompt 1:

**Lo que se le pidió:**  
Tomando el prototipo que tengo actualmente, refactoriza la capa de datos en `SaludData.kt`. Modifica el objeto `SaludRepository` para sustituir las listas estáticas por colecciones reactivas mediante `mutableStateListOf`, permitiendo sincronizar en tiempo real el catálogo de médicos y las citas reservadas entre la UI y el estado global. Entrega el código completo en Kotlin.

**Correcciones y ajustes realizados:**  
La propuesta inicial usaba un método `.add()` estándar. Se corrigió explícitamente para implementar `citasReservadas.add(0, cita)`, garantizando que cada nueva reserva realizada por el usuario se posicione en la cima del listado de forma inmediata.

---

## Prompt 2: Buscador Dinámico y Estado Vacío (RF-01)

**Lo que se le pidió:**  
Sobre la interfaz de `PantallaInicio.kt` creada en la Fase 1, conecta un `OutlinedTextField` funcional para realizar búsquedas dinámicas de médicos por nombre. Incluye un contenedor visual de estado vacío (*empty state*) con mensaje e ícono descriptivo cuando no existan coincidencias en la lista. Proporciona el composable completo.

**Correcciones y ajustes realizados:**  
Se ajustó la función de filtrado para que ignore mayúsculas, minúsculas y tildes, corrigiendo un comportamiento donde la búsqueda fallaba al ingresar caracteres con tilde o diferencias de caja.

---

## Prompt 3: Filtros por Especialidad estilo Cápsula (RF-01)

**Lo que se le pidió:**  
Rediseña la cabecera de `PantallaInicio.kt` integrando un `LazyRow` con componentes `FilterChip` estilo cápsula (*pill*) para filtrar el catálogo por especialidad ("Cardiología", "Pediatría", "Dermatología"). Asegura que la lista se filtre en tiempo real y que al abrir la app se muestren todos los médicos por defecto.

**Correcciones y ajustes realizados:**  
Se inicializó el estado `especialidadSeleccionada` con una cadena de texto vacía (`""`) en lugar de seleccionar una especialidad predeterminada, asegurando que la vista cargue el catálogo completo de doctores al inicio.

---

## Prompt 4:

**Lo que se le pidió:**  
Actualiza `PantallaDetalle.kt` para recibir y renderizar dinámicamente los datos del objeto `Doctor` seleccionado. Muestra el avatar circular con iniciales sobre fondo morado, especialidad, años de experiencia, calificación y la biografía completa del médico. Entrega la implementación del composable.

**Correcciones y ajustes realizados:**  
Se reestructuraron los márgenes y paddings internos del contenedor principal dentro del `LazyColumn`, corrigiendo un solapamiento donde el bloque de la biografía no dejaba espacio suficiente de separación con las tarjetas superiores.

---

## Prompt 5:

**Lo que se le pidió:**  
Corrige el layout en `PantallaDetalle.kt` para que el botón "Agendar cita" quede anclado de forma fija en la parte inferior de la pantalla sin superponerse con los botones de navegación o la barra gestual del sistema Android.

**Correcciones y ajustes realizados:**  
Se reubicó el botón dentro del slot `bottomBar` del `Scaffold` y se envolvió con el modificador `Modifier.navigationBarsPadding()`, elevando dinámicamente el componente la distancia exacta sobre la barra del sistema operativo.

---

## Prompt 6: Flujo de Selección de Fecha y Hora (RF-02)

**Lo que se le pidió:**  
Transforma `PantallaSeleccion.kt` en un módulo interactivo (RF-02). Configura la selección horizontal de fechas, la cuadrícula de turnos de hora disponibles y conecta la acción del botón para crear y registrar la nueva reserva directamente en `SaludRepository`. Proporciona el código completo.

**Correcciones y ajustes realizados:**  
Se agregó una validación en el estado local para mantener el botón "Confirmar cita" inhabilitado (`enabled = false`) hasta que el usuario haya seleccionado explícitamente tanto el día como el turno de hora.

---

## Prompt 7:

**Lo que se le pidió:**  
Resuelve el fallo de maquetado en `PantallaSeleccion.kt` donde el botón "Confirmar cita" se desplaza al centro de la pantalla. Asimismo, ajusta el contraste de los textos y de la `TopAppBar` para garantizar su visibilidad sobre fondo claro.

**Correcciones y ajustes realizados:**  
Se movió el botón al slot `bottomBar` del `Scaffold` e integró `navigationBarsPadding()`. Además, se parametrizaron explícitamente los atributos `color = Color.Black` en el título y `tint = Color.Black` en los íconos de la barra superior.

---

## Prompt 8: Confirmación de Reserva (RF-02)

**Lo que se le pidió:**  
Optimiza la pantalla de confirmación (`PantallaConfirmacion.kt` - RF-02) retirando la TopBar superior. Centra verticalmente los elementos visuales de éxito (ícono de check morado y resumen de la cita) y actualiza el botón "Volver al inicio" respetando los márgenes del sistema.

**Correcciones y ajustes realizados:**  
Se utilizó una disposición en `Column` con `Arrangement.Center` y `Alignment.CenterHorizontally`, aplicando el modificador `Modifier.navigationBarsPadding()` en el contenedor global para proteger la UI de la barra del sistema.

---

## Prompt 9: Historial de Citas (RF-04)

**Lo que se le pidió:**  
Conecta `PantallaMisCitas.kt` con la colección reactiva de `SaludRepository` (RF-04) para reflejar las reservas agendadas en tiempo real. Diseña las tarjetas agregando un indicador lateral morado de estado y la etiqueta alineada ("Confirmada" / "Completada"). Proporciona el código fuente.

**Correcciones y ajustes realizados:**  
Se reestructuró el maquetado en `Row` dentro de cada tarjeta para alinear simétricamente la barra vertical de estado con la información de fecha, hora y doctor, corrigiendo un desalineamiento visual en pantallas pequeñas.

---

## Prompt 10: Menú Lateral e Indicador de Sección (RF-03)

**Lo que se le pidió:**  
Integra en `AppNavigation.kt` un `ModalNavigationDrawer` funcional (RF-03). Configura la cabecera con el avatar e iniciales del usuario ("Alexis Prieto"), accesos directos con íconos estilo `RadioButton` e indicador de la sección seleccionada conectado con el `NavHost`. Entrega el archivo completo.

**Correcciones y ajustes realizados:**  
Se vinculó el estado de selección del menú lateral con la propiedad `currentDestination` de Navigation Compose para asegurar que el indicador visual resalte siempre la pantalla en la que se encuentra el usuario.

---

## Prompt 11:

**Lo que se le pidió:**  
Ajusta el tema visual de las pantallas secundarias (`PantallaDetalle.kt`, `PantallaSeleccion.kt`, `PantallaConfirmacion.kt`) aplicando fondo blanco puro (`Color.White`) y corrigiendo los colores de contraste en encabezados e íconos para evitar que se vuelvan invisibles.

**Correcciones y ajustes realizados:**  
Se configuró `containerColor = Color.White` en los componentes `Scaffold` y `TopAppBarDefaults`, forzando el color de texto e íconos en `Color.Black` para mantener una legibilidad adecuada.

---

## Prompt 12:

**Lo que se le pidió:**  
Desarrolla la pantalla de perfil de usuario en `PantallaPerfil.kt`. Vincula la cabecera con el avatar del paciente ("Alexis Prieto"), tarjetas resumen de métricas de salud/citas y el listado con las opciones de configuración de la cuenta. Entrega la implementación completa.

**Correcciones y ajustes realizados:**  
Se corrigió la distribución responsiva del contenedor de métricas aplicando `Modifier.weight(1f)` a cada tarjeta en la fila (`Row`), previniendo desbordamientos horizontales en dispositivos con menor resolución de ancho.

---

## Prompt 13:

**Lo que se le pidió:**  
Realiza la optimización final de estabilidad en la navegación global en `AppNavigation.kt`. Ajusta la acción de confirmación de cita para que al presionar "Volver al inicio" se ejecute un desapilamiento limpio de la pila (*back stack*), eliminando las pantallas intermedias del flujo de reserva.

**Correcciones y ajustes realizados:**  
Se configuró la acción mediante `popBackStack(PantallaInicioRoute, inclusive = false)` al completar una reserva. Esto eliminó las pantallas de detalle y selección del historial, evitando que el usuario quede atrapado al usar el botón de retroceso.