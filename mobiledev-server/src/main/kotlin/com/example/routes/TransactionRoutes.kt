package com.example.routes

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionRouting() {
    get("/transaction") {
        val id = getUserId(call.request.queryParameters)

        if (id != null) {
            call.respond(dao.allUserTransactions(id))
        }
        
        call.respond(dao.allTransactions())
    }
}

private fun getUserId(parameters: Parameters): Int? {
    return parameters["userid"]?.toInt()
}