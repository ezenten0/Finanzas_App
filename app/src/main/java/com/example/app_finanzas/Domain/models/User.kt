package com.example.app_finanzas.Domain.models

data class User(
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String?,
    val passwordHash: String,
    val salt: String
)