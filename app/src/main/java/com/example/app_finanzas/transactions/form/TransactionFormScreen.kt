@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.app_finanzas.transactions.form

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_finanzas.data.transaction.TransactionRepository
import com.example.app_finanzas.home.model.TransactionType
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionFormRoute(
    transactionRepository: TransactionRepository,
    transactionId: Int?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: TransactionFormViewModel = viewModel(
        factory = TransactionFormViewModel.Factory(transactionRepository, transactionId)
    )
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.saveSucceeded) {
        if (state.saveSucceeded) {
            onDismiss()
            viewModel.consumeSuccessFlag()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            scope.launch { snackbarHostState.showSnackbar(message) }
        }
    }

    TransactionFormScreen(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onAmountChange = viewModel::onAmountChange,
        onCategoryChange = viewModel::onCategoryChange,
        onTypeChange = viewModel::onTypeChange,
        onDateSelected = viewModel::onDateSelected,
        onSave = viewModel::saveTransaction,
        onDismiss = onDismiss,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TransactionFormScreen(
    state: TransactionFormUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val datePickerVisible = rememberSaveable { mutableStateOf(false) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("d 'de' MMMM yyyy", Locale("es", "ES")) }

    if (datePickerVisible.value) {
        DatePickerDialog(
            onDismissRequest = { datePickerVisible.value = false },
            confirmButton = {
                TextButton(onClick = { datePickerVisible.value = false }) {
                    Text(text = "Listo")
                }
            },
            dismissButton = {
                TextButton(onClick = { datePickerVisible.value = false }) {
                    Text(text = "Cancelar")
                }
            }
        ) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = state.date
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )
            DatePicker(
                state = datePickerState,
                title = { Text(text = "Selecciona la fecha") }
            )
            LaunchedEffect(datePickerState.selectedDateMillis) {
                val millis = datePickerState.selectedDateMillis ?: return@LaunchedEffect
                val selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                onDateSelected(selectedDate)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isEditing) "Editar movimiento" else "Nuevo movimiento",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Cerrar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AnimatedContent(
                targetState = state.type,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "typeAnimation"
            ) { type ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (type == TransactionType.INCOME) Icons.Rounded.ArrowUpward else Icons.Rounded.ArrowDownward,
                        contentDescription = null,
                        tint = if (type == TransactionType.INCOME) Color(0xFF2E7D32) else Color(0xFFC62828),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = if (type == TransactionType.INCOME) "Ingreso" else "Gasto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            SegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    selected = state.type == TransactionType.INCOME,
                    onClick = { onTypeChange(TransactionType.INCOME) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    icon = { Icon(imageVector = Icons.Rounded.ArrowUpward, contentDescription = null) }
                ) {
                    Text(text = "Ingreso")
                }
                SegmentedButton(
                    selected = state.type == TransactionType.EXPENSE,
                    onClick = { onTypeChange(TransactionType.EXPENSE) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    icon = { Icon(imageVector = Icons.Rounded.ArrowDownward, contentDescription = null) }
                ) {
                    Text(text = "Gasto")
                }
            }

            OutlinedTextField(
                value = state.title,
                onValueChange = onTitleChange,
                label = { Text(text = "Título") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Payments, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = onDescriptionChange,
                label = { Text(text = "Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.amount,
                onValueChange = onAmountChange,
                label = { Text(text = "Monto") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.CheckCircle, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = state.category,
                onValueChange = onCategoryChange,
                label = { Text(text = "Categoría") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = { datePickerVisible.value = true },
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Event, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = dateFormatter.format(state.date))
            }

            Spacer(modifier = Modifier.weight(1f, fill = true))

            AnimatedVisibility(visible = state.isSaving) {
                Text(
                    text = "Guardando movimiento...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = if (state.isEditing) "Actualizar" else "Guardar")
            }
        }
    }
}
