package com.example.app_finanzas.Activities.IntroActivity.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_finanzas.Activities.IntroActivity.screens.AuthIntroScreen // si usas otro ViewModel, ajusta import
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthIntroScreen(
    onAuthSuccess: () -> Unit = {}
) {
    // Para empezar, un toggle simple registro/login. Reemplaza con tu AuthViewModel cuando esté integrado.
    var isRegister by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Button(onClick = { isRegister = true }) { Text("Registrar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { isRegister = false }) { Text("Iniciar sesión") }
            }
            Spacer(Modifier.height(16.dp))

            if (isRegister) {
                Text("Formulario de registro (placeholder).")
                // Aqui renderizas los TextFields y botones del registro
                Button(onClick = onAuthSuccess, modifier = Modifier.fillMaxWidth()) {
                    Text("Registrar (demo)")
                }
            } else {
                Text("Formulario de login (placeholder).")
                Button(onClick = onAuthSuccess, modifier = Modifier.fillMaxWidth()) {
                    Text("Iniciar sesión (demo)")
                }
            }
        }
    }
}