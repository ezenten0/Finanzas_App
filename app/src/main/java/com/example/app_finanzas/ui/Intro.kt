package com.example.app_finanzas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.ViewModel.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun IntroScreen(viewModel: AuthViewModel, onAuthSuccess: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    var isRegister by remember { mutableStateOf(true) }
    var showPassword by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            // Toggle register/login
            Row {
                Button(onClick = { isRegister = true }) { Text("Registrar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { isRegister = false }) { Text("Iniciar sesión") }
            }
            Spacer(Modifier.height(16.dp))

            if (isRegister) {
                // Nombre
                OutlinedTextField(
                    value = state.nombre.value,
                    onValueChange = { viewModel.onNombreChanged(it) },
                    label = { Text("Nombre") },
                    isError = state.nombre.error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                state.nombre.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Apellido
                OutlinedTextField(
                    value = state.apellido.value,
                    onValueChange = { viewModel.onApellidoChanged(it) },
                    label = { Text("Apellido") },
                    isError = state.apellido.error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                state.apellido.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Email
                OutlinedTextField(
                    value = state.email.value,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo") },
                    isError = state.email.error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                state.email.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Telefono opcional
                OutlinedTextField(
                    value = state.telefono.value,
                    onValueChange = { viewModel.onTelefonoChanged(it) },
                    label = { Text("Teléfono (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Password
                OutlinedTextField(
                    value = state.password.value,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña") },
                    isError = state.password.error != null,
                    trailingIcon = {
                        Row {
                            if (state.password.error != null) {
                                Icon(Icons.Filled.Error, contentDescription = state.password.error, tint = MaterialTheme.colorScheme.error)
                            }
                            IconButton(onClick = { showPassword = !showPassword }) {
                                if (showPassword) Icon(Icons.Filled.Visibility, contentDescription = "Ocultar") else Icon(Icons.Filled.VisibilityOff, contentDescription = "Mostrar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                state.password.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Confirm password
                OutlinedTextField(
                    value = state.confirmPassword.value,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    label = { Text("Confirmar contraseña") },
                    isError = state.confirmPassword.error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                state.confirmPassword.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.submitRegister(
                            onSuccess = { onAuthSuccess() },
                            onError = { msg ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(msg)
                                }
                            }
                        )
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (state.isLoading) "Registrando..." else "Registrar")
                }
            } else {
                // Login: email + password
                OutlinedTextField(
                    value = state.email.value,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo") },
                    isError = state.email.error != null,
                    modifier = Modifier.fillMaxWidth()
                )
                state.email.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                OutlinedTextField(
                    value = state.password.value,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña") },
                    isError = state.password.error != null,
                    trailingIcon = {
                        Row {
                            if (state.password.error != null) {
                                Icon(Icons.Filled.Error, contentDescription = state.password.error, tint = MaterialTheme.colorScheme.error)
                            }
                            IconButton(onClick = { showPassword = !showPassword }) {
                                if (showPassword) Icon(Icons.Filled.Visibility, contentDescription = "Ocultar") else Icon(Icons.Filled.VisibilityOff, contentDescription = "Mostrar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                state.password.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    viewModel.submitLogin(state.email.value, state.password.value,
                        onSuccess = { onAuthSuccess() },
                        onError = { msg ->
                            scope.launch {
                                snackbarHostState.showSnackbar(msg)
                            }
                        }
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Iniciar sesión")
                }
            }
        }
    }
}
