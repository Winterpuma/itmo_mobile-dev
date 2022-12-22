package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Transaction
import com.example.models.Transactions
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        money = row[Users.money]
    )

    private fun resultRowToTransaction(row: ResultRow) = Transaction(
        id = row[Transactions.id].toString(),
        userId = row[Transactions.userId],
        description = row[Transactions.description],
        moneyDif = row[Transactions.moneyDif]
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun addNewUser(): User? = dbQuery {
        val insertStatement = Users.insert {
            it[money] = 0
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun deleteUser(id: Int): Boolean  = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    override suspend fun editUser(id: Int, money: Int): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.money] = money
        } > 0
    }

    override suspend fun allTransactions(): List<Transaction>  = dbQuery {
        Transactions.selectAll().map(::resultRowToTransaction)
    }

    override suspend fun allUserTransactions(userId: Int): List<Transaction> = dbQuery {
        Transactions
            .select { Transactions.userId eq userId }
            .map(::resultRowToTransaction)
    }

    override suspend fun addTransaction(id: UUID, userId: Int, description: String, moneyDif: Int): Transaction?  = dbQuery {
        val insertStatement = Transactions.insert {
            it[Transactions.id] = id
            it[Transactions.userId] = userId
            it[Transactions.description] = description
            it[Transactions.moneyDif] = moneyDif
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTransaction)
    }
}

val dao: DAOFacade = DAOFacadeImpl()
