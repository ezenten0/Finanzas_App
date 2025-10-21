package com.example.app_finanzas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_finanzas.Domain.models.User
import com.example.app_finanzas.util.HashUtils
import com.example.app_finanzas.util.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FieldState(val value: String = "", val error: String? = null, val touched: Boolean = false)
data class AuthUiState(
    val nombre: FieldState = FieldState(),
    val apellido: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val telefono: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val isLoading: Boolean = false,
    val submitAttempted: Boolean = false
)

class AuthViewModel(private val repo: com.example.app_finanzas.data.UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onNombreChanged(v: String) { _uiState.value = _uiState.value.copy(nombre = _uiState.value.nombre.copy(value = v)) }
    fun onApellidoChanged(v: String) { _uiState.value = _uiState.value.copy(apellido = _uiState.value.apellido.copy(value = v)) }
    fun onEmailChanged(v: String) { _uiState.value = _uiState.value.copy(email = _uiState.value.email.copy(value = v.trim().lowercase())) }
    fun onTelefonoChanged(v: String) { _uiState.value = _uiState.value.copy(telefono = _uiState.value.telefono.copy(value = v)) }
    fun onPasswordChanged(v: String) { _uiState.value = _uiState.value.copy(password = _uiState.value.password.copy(value = v)) }
    fun onConfirmPasswordChanged(v: String) { _uiState.value = _uiState.value.copy(confirmPassword = _uiState.value.confirmPassword.copy(value = v)) }

    fun onFieldBlur(field: String) {
        // marcar touched y validar inline
        when (field) {
            "email" -> {
                val ok = Validators.isValidEmail(_uiState.value.email.value)
                _uiState.value = _uiState.value.copy(email = _uiState.value.email.copy(touched = true, error = if (!ok) "Correo no válido" else null))
            }
            "nombre" -> {
                val ok = Validators.isValidName(_uiState.value.nombre.value)
                _uiState.value = _uiState.value.copy(nombre = _uiState.value.nombre.copy(touched = true, error = if (!ok) "Nombre requerido" else null))
            }
            "apellido" -> {
                val ok = Validators.isValidName(_uiState.value.apellido.value)
                _uiState.value = _uiState.value.copy(apellido = _uiState.value.apellido.copy(touched = true, error = if (!ok) "Apellido requerido" else null))
            }
            "password" -> {
                val ok = Validators.isValidPassword(_uiState.value.password.value)
                _uiState.value = _uiState.value.copy(password = _uiState.value.password.copy(touched = true, error = if (!ok) "La contraseña no cumple requisitos" else null))
            }
            "confirm" -> {
                val ok = _uiState.value.password.value == _uiState.value.confirmPassword.value
                _uiState.value = _uiState.value.copy(confirmPassword = _uiState.value.confirmPassword.copy(touched = true, error = if (!ok) "No coincide con la contraseña" else null))
            }
        }
    }

    private fun validateAll(): Boolean {
        val s = _uiState.value
        var ok = true
        // nombre
        if (!Validators.isValidName(s.nombre.value)) {
            _uiState.value = s.copy(nombre = s.nombre.copy(error = "Nombre requerido"))
            ok = false
        }
        if (!Validators.isValidName(s.apellido.value)) {
            _uiState.value = _uiState.value.copy(apellido = s.apellido.copy(error = "Apellido requerido"))
            ok = false
        }
        if (!Validators.isValidEmail(s.email.value)) {
            _uiState.value = _uiState.value.copy(email = s.email.copy(error = "Correo no válido"))
            ok = false
        }
        if (!Validators.isValidPassword(s.password.value)) {
            _uiState.value = _uiState.value.copy(password = s.password.copy(error = "La contraseña no cumple requisitos"))
            ok = false
        }
        if (s.password.value != s.confirmPassword.value) {
            _uiState.value = _uiState.value.copy(confirmPassword = s.confirmPassword.copy(error = "No coincide con la contraseña"))
            ok = false
        }
        return ok
    }

    fun submitRegister(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(submitAttempted = true)
        if (!validateAll()) {
            onError("Corrige los errores marcados")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val salt = HashUtils.generateSalt()
                val hash = HashUtils.hashPassword(_uiState.value.password.value, salt)
                val user = User(
                    nombre = _uiState.value.nombre.value.trim(),
                    apellido = _uiState.value.apellido.value.trim(),
                    email = _uiState.value.email.value.trim().lowercase(),
                    telefono = _uiState.value.telefono.value.ifBlank { null },
                    passwordHash = hash,
                    salt = salt
                )
                val res = repo.register(user)
                if (res.isSuccess) {
                    // autologin: guardar ya lo hizo el repo
                    onSuccess()
                } else {
                    onError(res.exceptionOrNull()?.message ?: "Error al registrar")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error inesperado")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun submitLogin(email: String, password: String, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // obtener usuario guardado y verificar
                repo.getSavedUserFlow().collect { saved ->
                    if (saved == null) {
                        onError("Usuario o contraseña incorrectos")
                    } else {
                        val ok = HashUtils.verifyPassword(password, saved.salt, saved.passwordHash)
                        if (ok && saved.email == email.trim().lowercase()) {
                            onSuccess(saved)
                        } else {
                            onError("Usuario o contraseña incorrectos")
                        }
                    }
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error de autenticación")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}