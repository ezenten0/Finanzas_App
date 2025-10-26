package com.example.app_finanzas.budgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.data.transaction.TransactionRepository
import com.example.app_finanzas.home.analytics.TransactionAnalytics
import com.example.app_finanzas.home.analytics.BudgetProgress
import com.example.app_finanzas.home.model.Transaction
import java.text.NumberFormat
import java.util.Locale

private val budgetNumberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
private val defaultBudgets = mapOf(
    "Alimentos" to 300.0,
    "Entretenimiento" to 120.0,
    "Social" to 150.0,
    "Inversiones" to 200.0
)

/**
 * Route that displays the progress of monthly budgets using the stored transactions.
 */
@Composable
fun BudgetsRoute(
    transactionRepository: TransactionRepository,
    modifier: Modifier = Modifier
) {
    val transactions by transactionRepository.observeTransactions().collectAsState(initial = emptyList())
    BudgetsScreen(transactions = transactions, modifier = modifier)
}

/**
 * Visualizes budget usage per category with progress indicators.
 */
@Composable
fun BudgetsScreen(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    val budgetProgress = remember(transactions) {
        TransactionAnalytics.calculateBudgetProgress(transactions, defaultBudgets)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Presupuestos", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(budgetProgress, key = { it.category }) { progress ->
                BudgetProgressCard(progress = progress)
            }
        }
    }
}

@Composable
private fun BudgetProgressCard(progress: BudgetProgress) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = progress.category, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            LinearProgressIndicator(
                progress = progress.progress.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${budgetNumberFormat.format(progress.spent)} de ${budgetNumberFormat.format(progress.limit)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
