package com.example.app_finanzas.auth

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_finanzas.data.user.UserProfile
import com.example.app_finanzas.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(AuthUiState())
    val uiState: State<AuthUiState> = _uiState

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            name = value,
            nameError = null,
            generalError = null
        )
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = null,
            generalError = null
        )
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            passwordError = null,
            generalError = null
        )
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = value,
            confirmPasswordError = null,
            generalError = null
        )
    }

    fun toggleMode() {
        val newMode = if (_uiState.value.mode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN
        _uiState.value = AuthUiState(mode = newMode)
    }

    fun onSubmit() {
        val state = _uiState.value
        val nameError = validateName(state)
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state)
        val confirmPasswordError = validateConfirmPassword(state)

        if (
            nameError != null ||
            emailError != null ||
            passwordError != null ||
            confirmPasswordError != null
        ) {
            _uiState.value = state.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitting = true, generalError = null)
            val result = withContext(Dispatchers.IO) {
                when (_uiState.value.mode) {
                    AuthMode.LOGIN -> userRepository.authenticate(state.email, state.password)
                    AuthMode.REGISTER -> userRepository.registerUser(state.name, state.email, state.password)
                }
            }

            result.fold(
                onSuccess = { handleAuthenticationSuccess(it) },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isSubmitting = false,
                        generalError = error.message ?: "Ha ocurrido un error inesperado."
                    )
                }
            )
        }
    }

    fun onAuthHandled() {
        _uiState.value = _uiState.value.copy(
            isSubmitting = false,
            authenticatedUser = null
        )
    }

    private fun handleAuthenticationSuccess(profile: UserProfile) {
        _uiState.value = _uiState.value.copy(
            isSubmitting = false,
            generalError = null,
            authenticatedUser = profile
        )
    }

    private fun validateName(state: AuthUiState): String? {
        return if (state.mode == AuthMode.REGISTER && state.name.isBlank()) {
            "El nombre es obligatorio."
        } else {
            null
        }
    }

    private fun validateEmail(email: String): String? {
        val trimmed = email.trim()
        if (trimmed.isEmpty()) {
            return "El correo es obligatorio."
        }
        return if (!Patterns.EMAIL_ADDRESS.matcher(trimmed).matches()) {
            "Ingresa un correo válido."
        } else {
            null
        }
    }

    private fun validatePassword(state: AuthUiState): String? {
        val password = state.password
        if (password.isEmpty()) {
            return "La contraseña es obligatoria."
        }

        return if (state.mode == AuthMode.REGISTER && !isStrongPassword(password)) {
            "Debe tener 8 caracteres, una mayúscula, una minúscula y un número."
        } else {
            null
        }
    }

    private fun validateConfirmPassword(state: AuthUiState): String? {
        return if (state.mode == AuthMode.REGISTER && state.password != state.confirmPassword) {
            "Las contraseñas no coinciden."
        } else {
            null
        }
    }

    private fun isStrongPassword(password: String): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= 8 && hasUppercase && hasLowercase && hasDigit
    }
}
