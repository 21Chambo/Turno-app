package com.turnofacil.model

data class TurnRequest(
    val nombre: String,
    val documento: String,
    val servicioId: Int,
    val hora: String
)
