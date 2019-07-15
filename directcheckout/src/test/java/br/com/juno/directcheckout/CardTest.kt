package br.com.juno.directcheckout

import org.junit.Test

class CardTest{



    @Test
    fun validateNumberTest(){
        CardsMock.visa.forEach {
            assert(it.validateNumber())
        }

        CardsMock.masterCard.forEach {
            assert(it.validateNumber())
        }

        CardsMock.amex.forEach {
            assert(it.validateNumber())
        }

        CardsMock.discover.forEach {
            assert(it.validateNumber())
        }

        CardsMock.hipercard.forEach {
            assert(it.validateNumber())
        }

        CardsMock.diners.forEach {
            assert(it.validateNumber())
        }

        CardsMock.jcb15.forEach {
            assert(it.validateNumber())
        }

        CardsMock.jcb16.forEach {
            assert(it.validateNumber())
        }

        CardsMock.elo.forEach {
            assert(it.validateNumber())
        }

        CardsMock.aura.forEach {
            assert(it.validateNumber())
        }
    }

    @Test
    fun validateCVCTest(){
        CardsMock.visa.forEach {
            assert(it.validateCVC())
        }

        CardsMock.masterCard.forEach {
            assert(it.validateCVC())
        }

        CardsMock.amex.forEach {
            assert(it.validateCVC())
        }

        CardsMock.discover.forEach {
            assert(it.validateCVC())
        }

        CardsMock.hipercard.forEach {
            assert(it.validateCVC())
        }

        CardsMock.diners.forEach {
            assert(it.validateCVC())
        }

        CardsMock.jcb15.forEach {
            assert(it.validateCVC())
        }

        CardsMock.jcb16.forEach {
            assert(it.validateCVC())
        }

        CardsMock.elo.forEach {
            assert(it.validateCVC())
        }

        CardsMock.aura.forEach {
            assert(it.validateCVC())
        }
    }

    @Test
    fun validateExpireDateTest(){
        CardsMock.visa.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.masterCard.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.amex.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.discover.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.hipercard.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.diners.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.jcb15.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.jcb16.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.elo.forEach {
            assert(it.validateExpireDate())
        }

        CardsMock.aura.forEach {
            assert(it.validateExpireDate())
        }
    }
}