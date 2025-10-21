package com.example.app_finanzas.data

import com.example.app_finanzas.Domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun register(user: User): Result<Unit>
    suspend fun login(email: String, password: String): Result<User>
    fun getSavedUserFlow(): Flow<User?>
    suspend fun clearUser()
}