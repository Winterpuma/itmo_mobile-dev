package com.example.mobile_dev.lab3

import com.example.mobile_dev.lab3.models.Transaction
import com.example.mobile_dev.lab3.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

class Requests {
    private val basicAddress = "http://10.0.2.2:8080"
    private val client = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json()
        }
    }

    fun getAllUsers(): List<User>{
        return runBlocking {
            return@runBlocking client.get("$basicAddress/user").body()
        }
    }

    fun getUser(id: Int): User? {
        val res = runBlocking {
            return@runBlocking client.get("$basicAddress/user/$id")
        }

        if (res.status == HttpStatusCode.OK)
            return runBlocking { return@runBlocking res.body() }
        else
            return null
    }

    fun createUser(): User {
        return runBlocking {
            return@runBlocking client.post("$basicAddress/user").body()
        }
    }

    fun deleteUser(id: Int): HttpResponse {
        return runBlocking {
            return@runBlocking client.delete("$basicAddress/user/$id")
        }
    }

    fun addMoney(id: Int, amount: Int): User {
        return runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/add/$amount").body()
        }
    }

    fun withdrawMoney(id: Int, amount: Int): User {
        return runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/withdraw/$amount").body()
        }
    }

    fun luckyMoney(id: Int): Pair<User?, String> {
        val res = runBlocking {
            return@runBlocking client.put("$basicAddress/user/$id/lucky")
        }
        
        if (res.status == HttpStatusCode.OK)
            return Pair(runBlocking { return@runBlocking res.body() }, "")
        else
            return Pair(null, runBlocking { return@runBlocking res.body() })
    }

    fun getAllUserTransactions(id: Int): List<Transaction>{
        return runBlocking {
            return@runBlocking client.get("$basicAddress/transaction?userid=$id").body()
        }
    }
}