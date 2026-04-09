package com.turnofacil.model

import com.google.gson.annotations.SerializedName

data class Service(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    @SerializedName("cupos_disponibles") val cuposDisponibles: Int,
    val horario: String
)
