package com.example.dao

import com.example.models.*
import java.util.UUID

interface DAOFacade {
    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun addNewUser(): User?
    suspend fun deleteUser(id: Int): Boolean
    suspend fun editUser(id: Int, money: Int): Boolean

    suspend fun addTransaction(id: UUID, userId: Int, description: String, moneyDif: Int): Transaction?
}