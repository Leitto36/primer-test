package com.primer.demo.repository.impl

import com.primer.demo.SqlOperationFailedException
import com.primer.demo.model.CardToken
import com.primer.demo.model.CardTokenStatus
import com.primer.demo.model.ProcessorType
import com.primer.demo.repository.CardTokenRepository
import java.util.UUID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class CardTokenRepositoryImpl : CardTokenRepository {

    override fun create(cardToken: CardToken): CardToken = transaction {
        CardTokenTable.insert { updateValues(it, cardToken) }
            .resultedValues
            ?.first()
            ?.toCardToken()
            ?: throw SqlOperationFailedException("CardToken not returned after insert.")
    }

    private object CardTokenTable : UUIDTable("card_token") {
        val cardId: Column<UUID> = uuid("card_id")
        val processorType: Column<ProcessorType> = enumeration(name = "processor_type_id", klass = ProcessorType::class)
        val token: Column<String> = text("token").index(customIndexName = "card_token_token_idx", isUnique = true)
        val status: Column<CardTokenStatus> = enumeration(name = "status_id", klass = CardTokenStatus::class)
    }

    private fun CardTokenTable.updateValues(
        it: UpdateBuilder<Number>,
        cardToken: CardToken
    ) {
        it[cardId] = cardToken.cardId
        it[processorType] = cardToken.processorType
        it[token] = cardToken.token
        it[status] = cardToken.status
    }

    private fun ResultRow.toCardToken() = CardToken(
        id = this[CardTokenTable.id].value,
        cardId = this[CardTokenTable.cardId],
        processorType = this[CardTokenTable.processorType],
        token = this[CardTokenTable.token],
        status = this[CardTokenTable.status]
    )
}
