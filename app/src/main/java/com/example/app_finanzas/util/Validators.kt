package com.example.app_finanzas.util

object Validators {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun isValidEmail(email: String): Boolean {
        val trimmed = email.trim().lowercase()
        return trimmed.length <= 254 && emailRegex.matches(trimmed)
    }

    fun isValidName(name: String): Boolean {
        val trimmed = name.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 50
    }

    fun isValidPhone(phone: String): Boolean {
        if (phone.isBlank()) return true // opcional
        return phone.length in 7..20 // regla simple, ajustar según país
    }

    fun isValidPassword(pw: String): Boolean {
        if (pw.length < 6) return false
        val hasUpper = pw.any { it.isUpperCase() }
        val hasLower = pw.any { it.isLowerCase() }
        val hasSpecial = pw.any { !it.isLetterOrDigit() }
        return hasUpper && hasLower && hasSpecial
    }
}