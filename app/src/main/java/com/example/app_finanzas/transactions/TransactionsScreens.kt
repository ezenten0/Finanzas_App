@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.app_finanzas.transactions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.data.transaction.TransactionRepository
import com.example.app_finanzas.home.model.Transaction
import com.example.app_finanzas.home.model.TransactionType
import com.example.app_finanzas.home.model.TransactionType.EXPENSE
import com.example.app_finanzas.home.model.TransactionType.INCOME
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "ES"))

/**
 * High level route that surfaces all transactions persisted in the database.
 */
@Composable
fun TransactionsRoute(
    transactionRepository: TransactionRepository,
    onTransactionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val transactions by transactionRepository.observeTransactions().collectAsState(initial = emptyList())
    TransactionsScreen(
        transactions = transactions,
        onTransactionSelected = onTransactionSelected,
        modifier = modifier
    )
}

/**
 * Displays the full transaction history with access to the detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    transactions: List<Transaction>,
    onTransactionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Historial de movimientos", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transactions, key = { it.id }) { transaction ->
                TransactionListItem(
                    transaction = transaction,
                    onClick = { onTransactionSelected(transaction.id) }
                )
            }
        }
    }
}

/**
 * Detail route that loads an individual transaction by id.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionDetailRoute(
    transactionRepository: TransactionRepository,
    transactionId: Int?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transactionState by produceState<Transaction?>(initialValue = null, transactionId) {
        value = transactionId?.let { transactionRepository.getTransactionById(it) }
    }

    TransactionDetailScreen(
        transaction = transactionState,
        onBack = onBack,
        modifier = modifier
    )
}

/**
 * Renders the transaction details highlighting the amount, category and date.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionDetailScreen(
    transaction: Transaction?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle del movimiento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        if (transaction == null) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No se encontró la transacción", style = MaterialTheme.typography.titleMedium)
                Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                    Text(text = "Volver")
                }
            }
        } else {
            TransactionDetailContent(
                transaction = transaction,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            )
        }
    }
}

/**
 * Breaks down the transaction details inside a card for better readability.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TransactionDetailContent(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodyLarge
            )
            AmountRow(label = "Monto", value = formatAmount(transaction))
            AmountRow(label = "Categoría", value = transaction.category)
            AmountRow(label = "Fecha", value = formatDate(transaction.date))
        }
    }
}

@Composable
private fun AmountRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

/**
 * Item used by the transaction list; it summarizes the entry and is clickable.
 */
@Composable
private fun TransactionListItem(
    transaction: Transaction,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = transaction.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(text = transaction.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = transaction.category, style = MaterialTheme.typography.labelLarge)
                Text(text = formatAmount(transaction), style = MaterialTheme.typography.titleMedium, color = amountColor(transaction.type))
            }
        }
    }
}

private fun formatAmount(transaction: Transaction): String {
    val sign = if (transaction.type == INCOME) 1 else -1
    return numberFormat.format(transaction.amount * sign)
}

@Composable
private fun amountColor(type: TransactionType) = if (type == INCOME) {
    MaterialTheme.colorScheme.primary
} else {
    MaterialTheme.colorScheme.error
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDate(raw: String): String {
    return runCatching {
        val parsed = LocalDate.parse(raw)
        parsed.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    }.getOrDefault(raw)
}
