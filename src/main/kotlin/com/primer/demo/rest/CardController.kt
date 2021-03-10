package com.primer.demo.rest

import com.primer.demo.rest.input.AddCardInput
import com.primer.demo.rest.mapper.toCardModel
import com.primer.demo.rest.mapper.toCardTokenOutput
import com.primer.demo.rest.output.CardTokenOutput
import com.primer.demo.service.CardService
import com.primer.demo.util.CARD_URL
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [CARD_URL])
class CardController(
    private val cardService: CardService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCard(@RequestBody addCardInput: AddCardInput) : CardTokenOutput = toCardTokenOutput(
        cardService.addCard(
            toCardModel(addCardInput)
        )
    )
}
