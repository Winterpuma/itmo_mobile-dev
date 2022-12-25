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

class Requests : IRequests {
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

    override suspend fun getAllUsers(): List<User>{
        return client.get("$basicAddress/user").body()
    }

    override suspend fun getUser(id: Int): User? {
        val res = client.get("$basicAddress/user/$id")

        if (res.status == HttpStatusCode.OK)
            return res.body()
        else
            return null
    }

    override suspend fun createUser(): User {
        return client.post("$basicAddress/user").body()
    }

    override suspend fun deleteUser(id: Int): HttpResponse {
        return client.delete("$basicAddress/user/$id")
    }

    override suspend fun addMoney(id: Int, amount: Int): User {
        return client.put("$basicAddress/user/$id/add/$amount").body()
    }

    override suspend fun withdrawMoney(id: Int, amount: Int): User {
        return client.put("$basicAddress/user/$id/withdraw/$amount").body()
    }

    override suspend fun luckyMoney(id: Int): Pair<User?, String> {
        val res = client.put("$basicAddress/user/$id/lucky")

        if (res.status == HttpStatusCode.OK)
            return Pair(res.body(), "")
        else
            return Pair(null, res.body())
    }

    override suspend fun getAllUserTransactions(id: Int): List<Transaction>{
        return client.get("$basicAddress/transaction?userid=$id").body()
    }
}

val requests: IRequests = Requests()