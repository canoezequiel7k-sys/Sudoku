# TEAM_CONTEXT — Sudoku (Android)

> Contexto breve para sesiones de IA. Leer esto primero; no re-explorar todo el proyecto.

## 1. Reglas (obligatorias)
- El humano es líder técnico: aprueba planes, revisa diffs, ejecuta pruebas y hace commits/push.
- Los agentes NO hacen commit, push, merge, cambio de rama ni operaciones destructivas.
- No sobrescribir cambios existentes. No inventar archivos, funciones ni dependencias.
- Una tarea = un agente escribiendo. Los demás solo leen.
- Código e identificadores en inglés; comentarios y documentación en español.
- Comentarios pedagógicos (qué, por qué, cómo se relaciona); nada redundante.
- Cambiar una regla de negocio requiere justificación y aprobación previa.
- Sin refactors grandes ni abstracciones innecesarias.

## 2. Roles
| Herramienta | Rol | Permisos |
|---|---|---|
| Antigravity (`agy`) | Arquitecto: diagnóstico, tareas pequeñas, criterios de aceptación | Solo lectura |
| OpenCode | Desarrollador: solo la tarea aprobada | Escritura limitada a los archivos permitidos |
| Gentle Shell | Revisor independiente del diff | Solo lectura |
| Copilot CLI | No operativo (errores 403) | No usar |

## 3. Formato de una tarea
Objetivo · Archivos permitidos · Qué NO tocar · Criterio de aceptación · Entrega: resumen de cambios + pruebas.

## 4. Ciclo
Diagnóstico → aprobación del plan → implementación → `git status`/`git diff` → revisión independiente → pruebas y compilación → correcciones → commit (humano).

## 5. Hechos verificados (inspección del código, MCP `filesystem-sudoku`)
- Stack: Kotlin, Jetpack Compose, Material 3, Navigation Compose 2.10.2. `minSdk` 24, `compileSdk`/`targetSdk` 37, Java 11.
- Paquete: `com.cursokotlin.sudoku`.
- Estructura: `domain/` (SudokuCell, SudokuGameData, SudokuProvider, SudokuScoreCalculator, SudokuUtils, SudokuValidator), `ui/composable/` (SudokuBlock, SudokuBoard, SudokuButton, SudokuCellItem, SudokuKeypad), `ui/navigation/SudokuNavigation.kt`, `ui/screen/` (GameScreen, MainScreen), `ui/theme/`, `MainActivity.kt`. La carpeta `data/` existe y está vacía.
- `SudokuScoreCalculator.kt` contiene solo la línea `package`; no tiene lógica.
- `SudokuProvider.kt` (`generateSudokuGame(difficulty: String)`): usa **una única solución fija** y la dificultad solo cambia qué celdas se ocultan, con posiciones también fijas (Fácil 5 huecos; Normal 24; Difícil 36). Los tableros no varían entre partidas. La dificultad se recibe como `String` (acepta "Fácil"/"Facil", "Difícil"/"Dificil"; cualquier otro valor cae en Normal).
- Pruebas: solo `ExampleUnitTest.kt` (2+2) y `ExampleInstrumentedTest.kt`. No hay pruebas del dominio.
- Dependencias de prueba: solo JUnit. No hay ViewModel ni lifecycle-viewmodel-compose en `build.gradle.kts`.

## 6. Hipótesis (NO verificadas; confirmar leyendo el código antes de actuar)
- La lógica y el estado del juego están concentrados en `GameScreen.kt`.
- La validación en `SudokuValidator.kt`/`SudokuUtils.kt` puede tener casos sin cubrir.
- Algunas opciones de menú o callbacks de diálogos podrían estar sin implementar.
- El temporizador y el ciclo de vida de la pantalla merecen revisión.
- Un ViewModel con estado de UI inmutable podría ayudar, pero hay que evaluar coste y necesidad.

## 7. Posible primera tarea (pendiente de aprobación)
Pruebas unitarias del dominio (`SudokuValidator`) sin modificar código existente, para aprender el flujo completo con bajo riesgo.

## 8. Pendientes
- Confirmar que el modelo de Gentle Shell quedó guardado en `settings.json`.
- Confirmar el estado de Git (`git status`) antes de asignar la primera tarea.
