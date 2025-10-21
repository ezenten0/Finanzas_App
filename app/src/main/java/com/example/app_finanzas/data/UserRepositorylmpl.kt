package com.example.app_finanzas.data

import com.example.app_finanzas.data.datastore.UserDataStore
import com.example.app_finanzas.Domain.models.User
import com.example.app_finanzas.util.HashUtils
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val ds: UserDataStore) : UserRepository {
    override suspend fun register(user: User): Result<Unit> {
        // validar duplicado local: si ya existe email, retornar failure
        val existing = ds.getUserFlow() // es un Flow; en impl real recolectar primero
        // Para esqueleto: asumir único usuario; comprobar en llamada real
        ds.saveUser(user)
        return Result.success(Unit)
    }

    override suspend fun login(email: String, password: String): Result<User> {
        val user = ds.getUserFlow() // en implementación real: first() para obtener valor actual
        // ÷ Aquí en esqueleto, se deberá recolectar el flow y verificar hash
        return Result.failure(Exception("Not implemented"))
    }

    override fun getSavedUserFlow(): Flow<User?> = ds.getUserFlow()

    override suspend fun clearUser() {
        ds.clear()
    }
}