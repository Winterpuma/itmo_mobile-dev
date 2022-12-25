package com.example.mobile_dev.lab3

import com.example.mobile_dev.lab3.models.Transaction
import com.example.mobile_dev.lab3.models.User
import io.ktor.client.statement.*

interface IRequests {
    suspend fun getAllUsers(): List<User>
    suspend fun getUser(id: Int): User?
    suspend fun createUser(): User
    suspend fun deleteUser(id: Int): HttpResponse

    suspend fun addMoney(id: Int, amount: Int): User
    suspend fun withdrawMoney(id: Int, amount: Int): User
    suspend fun luckyMoney(id: Int): Pair<User?, String>

    suspend fun getAllUserTransactions(id: Int): List<Transaction>
}