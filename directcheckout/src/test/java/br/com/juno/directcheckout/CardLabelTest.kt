package br.com.juno.directcheckout

import br.com.juno.directcheckout.model.CardLabel
import org.junit.Test

class CardLabelTest{

    @Test
    fun testVisaType(){
        CardsMock.visa.forEach {
            assert(it.getType() == CardLabel.VISA.name)
        }
    }

    @Test
    fun testMasterCardType(){
        CardsMock.masterCard.forEach {
            assert(it.getType() == CardLabel.MASTERCARD.name)
        }
    }

    @Test
    fun testAmexType(){
        CardsMock.amex.forEach {
            assert(it.getType() == CardLabel.AMEX.name)
        }
    }

    @Test
    fun testHiperCardType(){
        CardsMock.hipercard.forEach {
            assert(it.getType() == CardLabel.HIPERCARD.name)
        }
    }

    @Test
    fun testDinersType(){
        CardsMock.diners.forEach {
            assert(it.getType() == CardLabel.DINERS.name)
        }
    }

    @Test
    fun testJcb15Type(){
        CardsMock.jcb15.forEach {
            assert(it.getType() == CardLabel.JCB_15.name)
        }
    }

    @Test
    fun testJcb16Type(){
        CardsMock.jcb16.forEach {
            assert(it.getType() == CardLabel.JCB_16.name)
        }
    }

    @Test
    fun testEloType(){
        CardsMock.elo.forEach {
            assert(it.getType() == CardLabel.ELO.name)
        }
    }

    @Test
    fun testAuraType(){
        CardsMock.aura.forEach {
            assert(it.getType() == CardLabel.AURA.name)
        }
    }


}
