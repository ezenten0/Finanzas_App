package com.example.app_finanzas.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.app_finanzas.home.analytics.BalanceSummary
import com.example.app_finanzas.home.model.Transaction
import java.text.NumberFormat
import java.util.Locale

private val statsNumberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "ES"))

/**
 * Route that aggregates the stored transactions into high level statistics.
 */
@Composable
fun StatisticsRoute(
    transactionRepository: TransactionRepository,
    modifier: Modifier = Modifier
) {
    val transactions by transactionRepository.observeTransactions().collectAsState(initial = emptyList())
    StatisticsScreen(transactions = transactions, modifier = modifier)
}

/**
 * Visualizes total balance metrics and the distribution of expenses per category.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    val summary = remember(transactions) { TransactionAnalytics.calculateBalanceSummary(transactions) }
    val expensesByCategory = remember(transactions) { TransactionAnalytics.calculateExpenseByCategory(transactions) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Estadísticas", fontWeight = FontWeight.Bold) },
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
            item {
                BalanceOverviewCard(summary = summary)
            }
            if (expensesByCategory.isNotEmpty()) {
                item {
                    Text(
                        text = "Gastos por categoría",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                items(expensesByCategory.entries.toList(), key = { it.key }) { (category, amount) ->
                    CategoryStatRow(category = category, amount = amount)
                }
            } else {
                item {
                    Text(
                        text = "Aún no se registran gastos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun BalanceOverviewCard(summary: BalanceSummary) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Balance actual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = statsNumberFormat.format(summary.totalBalance), style = MaterialTheme.typography.headlineSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Ingresos", style = MaterialTheme.typography.labelLarge)
                    Text(text = statsNumberFormat.format(summary.totalIncome), style = MaterialTheme.typography.bodyLarge)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Gastos", style = MaterialTheme.typography.labelLarge)
                    Text(text = statsNumberFormat.format(summary.totalExpense), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
private fun CategoryStatRow(category: String, amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category, style = MaterialTheme.typography.bodyLarge)
            Text(text = statsNumberFormat.format(amount), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}
