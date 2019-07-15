package br.com.juno.directcheckout

import br.com.juno.directcheckout.model.CardLabel
import org.junit.Test

class CardLabelTest{

    @Test
    fun testVisaType(){
        CardsMock.visa.forEach {
            assert(it.getType() is CardLabel.VISA)
        }
    }

    @Test
    fun testMasterCardType(){
        CardsMock.masterCard.forEach {
            assert(it.getType() is CardLabel.MASTERCARD)
        }
    }

    @Test
    fun testAmexType(){
        CardsMock.amex.forEach {
            assert(it.getType() is CardLabel.AMEX)
        }
    }

    @Test
    fun testHiperCardType(){
        CardsMock.hipercard.forEach {
            assert(it.getType() is CardLabel.HIPERCARD)
        }
    }

    @Test
    fun testDinersType(){
        CardsMock.diners.forEach {
            assert(it.getType() is CardLabel.DINERS)
        }
    }

    @Test
    fun testJcb15Type(){
        CardsMock.jcb15.forEach {
            assert(it.getType() is CardLabel.JCB_15)
        }
    }

    @Test
    fun testJcb16Type(){
        CardsMock.jcb16.forEach {
            assert(it.getType() is CardLabel.JCB_16)
        }
    }

    @Test
    fun testEloType(){
        CardsMock.elo.forEach {
            assert(it.getType() is CardLabel.ELO)
        }
    }

    @Test
    fun testAuraType(){
        CardsMock.aura.forEach {
            assert(it.getType() is CardLabel.AURA)
        }
    }


}