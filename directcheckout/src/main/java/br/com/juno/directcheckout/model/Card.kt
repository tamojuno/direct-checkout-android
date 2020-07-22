package br.com.juno.directcheckout.model

import br.com.juno.directcheckout.utils.CardUtils

class Card (cardNumber: String,
            val holderName: String,
            val securityCode: String,
            val expirationMonth: String,
            val expirationYear: String){

    val cardNumber = cardNumber.replace(" ", "")

    fun getType() = CardUtils.getCardType(cardNumber)?.name

    fun validateCVC() = CardUtils.validateCVC(cardNumber, securityCode)

    fun validateNumber() = CardUtils.validateNumber(cardNumber)

    fun validateExpireDate() = CardUtils.validateExpireDate(expirationMonth, expirationYear)

}

fun Card.toMap():Map<String,Any>  = mapOf(
    "cardNumber" to cardNumber,
    "holderName" to holderName,
    "securityCode" to securityCode,
    "expirationMonth" to expirationMonth,
    "expirationYear" to expirationYear
)
