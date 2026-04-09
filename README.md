# 🍔 TurnoFácil - Puesto de Comida

Aplicación móvil con backend para gestionar turnos en un puesto de comida.  
El sistema permite consultar los servicios o productos disponibles, ver su información y solicitar un turno de atención desde una app Android conectada a una API REST.

---

## 📋 Tabla de Contenidos

- Descripción del Proyecto
- Funcionalidades
- Tecnologías Utilizadas
- Requisitos Previos
- Instalación y Configuración
- Configuración de la Base de Datos
- Ejecución del Proyecto
- Estructura del Proyecto
- Endpoints de la API
- Flujo de Uso
- Posibles Errores y Soluciones
- Evidencias del Proyecto

---

## 📌 Descripción del Proyecto

Este proyecto fue desarrollado como un MVP técnico funcional para el caso **TurnoFácil**, adaptado a un **puesto de comida**.  
La aplicación permite que un usuario consulte los servicios disponibles, revise el detalle de cada uno y solicite un turno registrando sus datos básicos.  
El backend se encarga de exponer la información mediante una API REST y almacenar los turnos en PostgreSQL.

---

## ✨ Funcionalidades

- Visualización de una lista de servicios del puesto de comida
- Consulta del detalle de cada servicio
- Registro de turnos desde la app móvil
- Validación de datos obligatorios en el backend
- Restricción de solicitud cuando no hay cupos disponibles
- Comunicación entre app Android y backend local
- Almacenamiento de datos en PostgreSQL

---

## 🛠 Tecnologías Utilizadas

### Backend
- Node.js** - Entorno de ejecución para JavaScript
- Express.js - Framework para construir la API REST
- PostgreSQL- Sistema de base de datos relacional
- pg - Librería de conexión entre Node.js y PostgreSQL
- cors - Middleware para permitir el acceso desde la app cliente

### Frontend Android
- Kotlin  - Lenguaje principal de desarrollo Android
- Retrofit2 - Cliente HTTP para consumir la API
- Gson - Conversión de datos JSON
- AndroidX - Componentes base para Android moderno
- Material Design - Componentes visuales de la interfaz

---

## ✅ Requisitos Previos

Antes de ejecutar el proyecto, debes tener instalado:

- Node.js
- npm
- PostgreSQL
- Android Studio
- Un emulador Android o un dispositivo físico

---

## Instalación y Configuración

cd turnofacil
Ejecución del Proyecto
1. Levantar el Backend
cd backend
npm install
npm start
Respuesta esperada:
Servidor corriendo en puerto 3000 Base de datos lista Servicios iniciales creados

## Ejecutar la App Android

Usando el Emulador de Android Studio

Abrir Android Studio
Abrir el proyecto (ubicado en la carpeta app-Android)
Esperar a que se sincronicen las dependencias
Crear un emulador (si no tienes uno):
Tools → AVD Manager
Create Virtual Device
Seleccionar un dispositivo (ej: Pixel 6)
Seleccionar una imagen del sistema (API 30 o superior)

## Ejecutar la app:
Botón ▶️ "Run" 

## Verificar que todo funciona

✅ El backend muestra "Servidor corriendo en puerto 3000"

✅ La base de datos PostgreSQL está corriendo

✅ La app Android muestra la lista de servicios

✅ Puedes ver detalles y reservar turnos