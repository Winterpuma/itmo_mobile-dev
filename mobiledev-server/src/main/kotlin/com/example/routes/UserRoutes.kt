package com.example.routes

import com.example.MAX_PRICE
import com.example.MIN_BALANCE_TO_PLAY
import com.example.MIN_PRICE
import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.userRouting() {
    route("/user") {
        get {
            if (userStorage.isNotEmpty()) {
                call.respond(userStorage)
            } else {
                call.respondText("No users found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = getId(call.parameters) ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val user =
                userStorage.find { it.id == id } ?: return@get call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(user)
        }

        post {
            val user = User(userId++, 0)
            userStorage.add(user)
            call.respond(user)
        }

        delete("{id?}") {
            val id = getId(call.parameters) ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (userStorage.removeIf { it.id == id }) {
                call.respondText("User removed correctly", status = HttpStatusCode.OK)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Route.userMoneyRouting() {
    route("/user/{id}") {
        put("add/{money}") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val money = call.parameters["money"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user =
                userStorage.find { it.id == id } ?: return@put call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )

            user.money += money.toInt()
            val transaction = Transaction(UUID.randomUUID().toString(), user.id, "Add", money.toInt())
            transactionStorage.add(transaction)
            call.respond(user)
        }

        put("withdraw/{money}") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val moneyToWithdraw = call.parameters["money"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user =
                userStorage.find { it.id == id } ?: return@put call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )

            if (user.money < moneyToWithdraw)
                return@put call.respondText (
                    "User $id doesn't have enough money",
                    status = HttpStatusCode.NotAcceptable
                )

            user.money -= moneyToWithdraw
            val transaction = Transaction(UUID.randomUUID().toString(), user.id, "Withdraw", -moneyToWithdraw)
            transactionStorage.add(transaction)
            call.respond(user)
        }

        put("lucky") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user =
                userStorage.find { it.id == id } ?: return@put call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )

            if (user.money < MIN_BALANCE_TO_PLAY)
                return@put call.respondText (
                    "User $id doesn't have enough money. Minimum amount is $MIN_BALANCE_TO_PLAY",
                    status = HttpStatusCode.NotAcceptable
                )

            val randomMoney = (MIN_PRICE..MAX_PRICE).random()
            user.money += randomMoney
            val msg = if (randomMoney < 0) "Lost" else if (randomMoney > 0) "Won" else "Sheesh! Zero"
            val transaction = Transaction(UUID.randomUUID().toString(), user.id, msg, randomMoney)
            transactionStorage.add(transaction)
            call.respond(user)
        }
    }
}

private fun getId(parameters: Parameters): Int? {
    return parameters["id"]?.toInt()
}