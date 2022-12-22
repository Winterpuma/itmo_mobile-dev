package com.example.routes

import com.example.MAX_PRICE
import com.example.MIN_BALANCE_TO_PLAY
import com.example.MIN_PRICE
import com.example.dao.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.userRouting() {
    route("/user") {
        get {
            call.respond(dao.allUsers())
        }

        get("{id?}") {
            val id = getId(call.parameters) ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing id")
            val user = dao.user(id)
            if (user != null)
                call.respond(user)
            else
                call.respond(HttpStatusCode.NotFound, "No user with id $id")
        }

        post {
            val user = dao.addNewUser()
            if (user != null)
                call.respond(HttpStatusCode.Created, user)
            else
                call.respond(HttpStatusCode.InternalServerError, "Cant create user")
        }

        delete("{id?}") {
            val id = getId(call.parameters) ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing id")
            val user = dao.user(id)

            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "No user with id $id")
            } else if (dao.deleteUser(id)) {
                call.respond("User removed correctly")
            } else {
                call.respond(HttpStatusCode.InternalServerError,"Can't delete user")
            }
        }
    }
}

fun Route.userMoneyRouting() {
    route("/user/{id}") {
        put("add/{money}") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val money = call.parameters["money"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user = dao.user(id) ?: return@put call.respond(HttpStatusCode.NotFound, "No user with id $id")

            user.money += money.toInt()
            dao.editUser(user.id, user.money)
            dao.addTransaction(UUID.randomUUID(), user.id, "Add", money.toInt())
            call.respond(user)
        }

        put("withdraw/{money}") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val moneyToWithdraw = call.parameters["money"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user = dao.user(id) ?: return@put call.respond(HttpStatusCode.NotFound, "No user with id $id")

            if (user.money < moneyToWithdraw)
                return@put call.respond (HttpStatusCode.NotAcceptable,"User $id doesn't have enough money")

            user.money -= moneyToWithdraw
            dao.editUser(user.id, user.money)
            dao.addTransaction(UUID.randomUUID(), user.id, "Withdraw", -moneyToWithdraw)
            call.respond(user)
        }

        put("lucky") {
            val id = getId(call.parameters) ?: return@put call.respond(HttpStatusCode.BadRequest)
            val user = dao.user(id) ?: return@put call.respond(HttpStatusCode.NotFound, "No user with id $id")


            if (user.money < MIN_BALANCE_TO_PLAY)
                return@put call.respond (HttpStatusCode.NotAcceptable,
                    "User $id doesn't have enough money. Minimum amount is $MIN_BALANCE_TO_PLAY")

            val randomMoney = (MIN_PRICE..MAX_PRICE).random()
            val msg = if (randomMoney < 0) "Lost" else if (randomMoney > 0) "Won" else "Sheesh! Zero"

            user.money += randomMoney
            dao.editUser(user.id, user.money)
            dao.addTransaction(UUID.randomUUID(), user.id, msg, randomMoney)
            call.respond(user)
        }
    }
}

private fun getId(parameters: Parameters): Int? {
    return parameters["id"]?.toInt()
}