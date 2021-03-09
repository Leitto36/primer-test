package com.primer.demo.repository.impl

import com.primer.demo.SqlOperationFailedException
import com.primer.demo.model.Card
import com.primer.demo.repository.CardRepository
import java.util.UUID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class CardRepositoryImpl : CardRepository {

    override fun create(card: Card): Card = transaction {
        CardTable.insert { updateValues(it, card) }
            .resultedValues
            ?.first()
            ?.toCard()
            ?: throw SqlOperationFailedException("Card not returned after insert.")
    }

    private object CardTable : UUIDTable("card") {
        val merchantId: Column<UUID> = uuid("merchant_id").index("card_token_merchant_id_idx")
        val cardHolderName: Column<String> = text("card_holder_name")
        val number: Column<String> = text("number")
        val expirationMonth: Column<Short> = short("expiration_month")
        val expirationYear: Column<Short> = short("expiration_year")
        val cvv: Column<Short> = short("cvv")
    }

    private fun CardTable.updateValues(
        it: UpdateBuilder<Number>,
        card: Card
    ) {
        it[merchantId] = card.merchantId
        it[cardHolderName] = card.cardHolderName
        it[number] = card.number
        it[expirationMonth] = card.expirationMonth
        it[expirationYear] = card.expirationYear
        it[cvv] = card.cvv
    }

    private fun ResultRow.toCard() = Card(
        id = this[CardTable.id].value,
        merchantId = this[CardTable.merchantId],
        cardHolderName = this[CardTable.cardHolderName],
        number = this[CardTable.number],
        expirationMonth = this[CardTable.expirationMonth],
        expirationYear = this[CardTable.expirationYear],
        cvv = this[CardTable.cvv]
    )
}
