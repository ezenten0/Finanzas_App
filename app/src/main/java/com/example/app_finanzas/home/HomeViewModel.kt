package com.example.app_finanzas.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.app_finanzas.home.model.HomeUiState
import com.example.app_finanzas.home.model.Transaction
import com.example.app_finanzas.home.model.TransactionType

class HomeViewModel : ViewModel() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        val transactions = listOf(
            Transaction(
                id = 1,
                title = "Pago de salario",
                description = "Depósito mensual de tu trabajo",
                amount = 1450.0,
                type = TransactionType.INCOME,
                category = "Salario",
                date = "5 Oct"
            ),
            Transaction(
                id = 2,
                title = "Supermercado",
                description = "Compra semanal",
                amount = 210.5,
                type = TransactionType.EXPENSE,
                category = "Alimentos",
                date = "6 Oct"
            ),
            Transaction(
                id = 3,
                title = "Freelance diseño",
                description = "Proyecto UX/UI",
                amount = 380.0,
                type = TransactionType.INCOME,
                category = "Freelance",
                date = "7 Oct"
            ),
            Transaction(
                id = 4,
                title = "Suscripción streaming",
                description = "Plan familiar",
                amount = 12.99,
                type = TransactionType.EXPENSE,
                category = "Entretenimiento",
                date = "8 Oct"
            ),
            Transaction(
                id = 5,
                title = "Cena con amigos",
                description = "Restaurante centro",
                amount = 48.25,
                type = TransactionType.EXPENSE,
                category = "Social",
                date = "8 Oct"
            ),
            Transaction(
                id = 6,
                title = "Intereses cuenta",
                description = "Rendimiento mensual",
                amount = 25.75,
                type = TransactionType.INCOME,
                category = "Inversiones",
                date = "9 Oct"
            )
        )

        updateState(transactions)
    }

    private fun updateState(transactions: List<Transaction>) {
        val totalIncome = transactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }
        val totalExpense = transactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }

        _uiState.value = HomeUiState(
            totalBalance = totalIncome - totalExpense,
            totalIncome = totalIncome,
            totalExpense = totalExpense,
            transactions = transactions
        )
    }
}
