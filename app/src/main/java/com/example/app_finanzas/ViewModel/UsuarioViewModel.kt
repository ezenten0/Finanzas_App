package com.example.app_finanzas.ViewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.app_finanzas.model.UsuarioErrores
import com.example.app_finanzas.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.FileOutputStream

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

    fun guardarDatosEnArchivo(context: Context) {
        val estadoActual = _estado.value
        val data = """
        Nombre: ${estadoActual.nombre}
        Correo: ${estadoActual.correo}
        Teléfono: ${estadoActual.telefono}
        
    """.trimIndent()

        try {
            val fileName = "usuarios.txt"

            // Para Android 10+ usamos MediaStore.Downloads
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(android.provider.MediaStore.Downloads.MIME_TYPE, "text/plain")
                    put(android.provider.MediaStore.Downloads.IS_PENDING, 1)
                }

                val collection = android.provider.MediaStore.Downloads.getContentUri(android.provider.MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val itemUri = resolver.insert(collection, contentValues)

                itemUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { output ->
                        output.write(data.toByteArray())
                    }
                    contentValues.clear()
                    contentValues.put(android.provider.MediaStore.Downloads.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }

            } else {
                // Para Android 9 o menor: usamos el directorio público de Descargas
                val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
                val file = java.io.File(downloadsDir, fileName)
                java.io.FileOutputStream(file, true).use { fos ->
                    fos.write(data.toByteArray())
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Validacion global formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.length < 3) "3 caracteres minimo" else null,
            correo = if (estadoActual.correo.length < 3 || !estadoActual.correo.contains("@")) "Debe contener @ y 3 caracteres minimos" else null,
            clave = if (estadoActual.clave.length < 6) "Clave debe tener al menos 6 caracteres" else null,
            telefono = if (estadoActual.telefono.length !in 9..13 || !estadoActual.telefono.startsWith("+")) "Debe contener entre 9 y 13 caracteres y empezar por '+'" else null
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