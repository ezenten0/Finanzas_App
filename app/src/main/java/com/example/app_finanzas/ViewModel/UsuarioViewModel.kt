package com.example.app_finanzas.ViewModel

import androidx.lifecycle.ViewModel
import com.example.app_finanzas.model.UsuarioErrores
import com.example.app_finanzas.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    //Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())

    //Estado externo inmutable
    val estado: StateFlow<UsuarioUiState> = _estado

    //Actualiza el nombre y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(nombre = valor, errores = it.errores.copy(nombre = null))
        }
    }

    //Actualiza campo correo
    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(correo = valor, errores = it.errores.copy(correo = null))
        }
    }

    //Actualiza campo clave
    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(clave = valor, errores = it.errores.copy(clave = null))
        }
    }

    //Actualiza campo telefono
    fun onTelefonoChange(valor: String) {
        _estado.update {
            it.copy(telefono = valor, errores = it.errores.copy(telefono = null))
        }
    }

    //Actualiza checkbox aceptacion terminos
    fun onAceptaTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(aceptaTerminos = valor)
        }
    }

    //Validacion global formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Nombre requerido" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Clave debe tener al menos 6 caracteres" else null,
            telefono = if (estadoActual.telefono.isBlank()) "Campo obligatorio" else null
        )
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.telefono
        ).isNotEmpty()
        _estado.update {
            it.copy(errores = errores)
        }
        return !hayErrores
    }
}