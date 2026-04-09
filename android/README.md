# TurnoFácil Android

Aplicación Android nativa en Kotlin para el MVP de gestión de turnos de un puesto de comida.

## Tecnologías
- Kotlin
- AndroidX
- Material Design
- Retrofit2
- Gson

## Flujo del MVP
1. Lista de servicios
2. Detalle del servicio
3. Solicitar turno
4. Confirmación

## Antes de ejecutar
En `RetrofitClient.kt` la URL base está configurada para emulador Android:
```kotlin
private const val BASE_URL = "http://10.0.2.2:3000/"
```
Si usas un celular físico, cambia esa IP por la IP local de tu computador, por ejemplo:
```kotlin
private const val BASE_URL = "http://192.168.1.50:3000/"
```

## Cómo ejecutar
1. Abre la carpeta `android` en Android Studio.
2. Sincroniza Gradle.
3. Ejecuta el backend en Node.js.
4. Corre la app en emulador o dispositivo.

## Pantallas incluidas
- Lista de servicios (mínimo 6 servicios)
- Detalle del servicio (nombre, descripción, cupos, horario)
- Solicitar turno (nombre, documento, hora actual)

## Regla funcional implementada
Si un servicio tiene `cupos = 0`, se muestra `Sin cupos` y se bloquea la solicitud.
