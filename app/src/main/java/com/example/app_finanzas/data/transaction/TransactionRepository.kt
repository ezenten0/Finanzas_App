package com.example.app_finanzas.data.transaction

import com.example.app_finanzas.data.local.transaction.TransactionDao
import com.example.app_finanzas.data.local.transaction.TransactionEntity
import com.example.app_finanzas.home.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Repository that orchestrates transaction persistence and exposes the domain
 * models used by the UI layer. The repository encapsulates all mapping logic
 * between Room entities and Compose-friendly models so the presentation layer
 * remains lightweight.
 */
class TransactionRepository(
    private val transactionDao: TransactionDao
) {

    /**
     * Observes every transaction stored locally and transforms the result into
     * UI models for immediate consumption by the different screens.
     */
    fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    /**
     * Ensures the database contains a baseline data set; this keeps the demo
     * experience rich without requiring the user to add manual entries first.
     */
    suspend fun ensureSeedData() {
        withContext(Dispatchers.IO) {
            if (transactionDao.countTransactions() == 0) {
                transactionDao.upsertTransactions(TransactionSamples.defaultTransactions())
            }
        }
    }

    /**
     * Retrieves a single transaction for the detail screen, returning null if
     * the identifier no longer exists in the database.
     */
    suspend fun getTransactionById(transactionId: Int): Transaction? {
        return withContext(Dispatchers.IO) {
            transactionDao.getTransactionById(transactionId)?.toDomain()
        }
    }

    /**
     * Inserts a transaction list, typically used by tests or future sync flows.
     */
    suspend fun upsertTransactions(transactions: List<Transaction>) {
        withContext(Dispatchers.IO) {
            val entities = transactions.map { it.toEntity() }
            transactionDao.upsertTransactions(entities)
        }
    }

    private fun TransactionEntity.toDomain(): Transaction {
        return Transaction(
            id = id,
            title = title,
            description = description,
            amount = amount,
            type = TransactionTypeMapper.fromStorage(type),
            category = category,
            date = date
        )
    }

    private fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            id = id,
            title = title,
            description = description,
            amount = amount,
            type = TransactionTypeMapper.toStorage(type),
            category = category,
            date = date
        )
    }
}
