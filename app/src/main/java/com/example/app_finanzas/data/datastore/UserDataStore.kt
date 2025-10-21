package com.example.app_finanzas.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.app_finanzas.Domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataStore(private val context: Context) {

    companion object {
        val KEY_NOMBRE = stringPreferencesKey("nombre")
        val KEY_APELLIDO = stringPreferencesKey("apellido")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_TELEFONO = stringPreferencesKey("telefono")
        val KEY_HASH = stringPreferencesKey("password_hash")
        val KEY_SALT = stringPreferencesKey("password_salt")
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NOMBRE] = user.nombre
            prefs[KEY_APELLIDO] = user.apellido
            prefs[KEY_EMAIL] = user.email
            prefs[KEY_TELEFONO] = user.telefono ?: ""
            prefs[KEY_HASH] = user.passwordHash
            prefs[KEY_SALT] = user.salt
        }
    }

    fun getUserFlow(): Flow<User?> = context.dataStore.data.map { prefs ->
        val email = prefs[KEY_EMAIL] ?: return@map null
        val hash = prefs[KEY_HASH] ?: return@map null
        val salt = prefs[KEY_SALT] ?: return@map null
        User(
            nombre = prefs[KEY_NOMBRE] ?: "",
            apellido = prefs[KEY_APELLIDO] ?: "",
            email = email,
            telefono = prefs[KEY_TELEFONO]?.ifBlank { null },
            passwordHash = hash,
            salt = salt
        )
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}