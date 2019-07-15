package br.com.juno.directcheckout.model

import br.com.juno.directcheckout.utils.CardUtils
import com.squareup.moshi.Json
import java.util.*

class Card (cardNumber: String,
            @Json(name = "holderName") val holderName: String,
            @Json(name = "securityCode") val securityCode: String,
            @Json(name = "expirationMonth") val expirationMonth: String,
            @Json(name = "expirationYear") val expirationYear: String){

    @Json(name = "cardNumber") val cardNumber = cardNumber.replace(" ", "")

    fun getType() = CardUtils.getCardType(cardNumber)?.name

    fun validateCVC() = CardUtils.validateCVC(cardNumber, securityCode)

    fun validateNumber() = CardUtils.validateNumber(cardNumber)

    fun validateExpireDate() = CardUtils.validateExpireDate(expirationMonth, expirationYear)

}
