package com.example.app_finanzas.home.model

enum class TransactionType {
    INCOME,
    EXPENSE
}

data class Transaction(
    val id: Int,
    val title: String,
    val description: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: String
)
