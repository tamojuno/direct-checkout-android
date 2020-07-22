package br.com.juno.directcheckout.utils

import br.com.juno.directcheckout.model.CardLabel
import java.util.*

internal object CardUtils {

    fun getCardType(cardNumber: String) = CardLabel.getOrderedLabels().find {
        it.detector.containsMatchIn(cardNumber.replace(" ", ""))
    }

    fun validateCVC(cardNumber: String, securityCode: String) =
        securityCode.length == getCardType(cardNumber)?.cvcLength

    fun validateNumber(cardNumber: String): Boolean {
        val cardNo = cardNumber.replace(" ", "")
        val type = getCardType(cardNumber)
        return type != null && type.cardLength == cardNo.length && validateNum(cardNo)
    }

    fun validateExpireDate(expirationMonth: String, expirationYear: String): Boolean {
        val today = Calendar.getInstance()
        val month = today.get(Calendar.MONTH)
        val year = today.get(Calendar.YEAR)
        if (expirationMonth.toInt() > 0 && expirationYear.toInt() > 0 && expirationYear.toInt() >= year) {
            if (expirationYear.toInt() == year) {
                return (expirationMonth.toInt() > month)
            }
            return true
        }
        return false
    }

    private fun validateNum(cardNo: String): Boolean {
        var checkSumTotal = 0

        for (digitCounter in cardNo.length - 1 downTo 0 step 2) {
            checkSumTotal += cardNo[digitCounter].toString().toInt()
            runCatching {
                (cardNo[digitCounter - 1].toString().toInt() * 2).toString().forEach {
                    checkSumTotal += it.toString().toInt()
                }
            }
        }
        return (checkSumTotal % 10 == 0)
    }

}