package br.com.juno.directcheckout.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.juno.directcheckout.DirectCheckout
import br.com.juno.directcheckout.model.Card
import br.com.juno.directcheckout.model.DirectCheckoutException
import br.com.juno.directcheckout.model.DirectCheckoutListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card = Card(
            cardNumber = "5191865557386780",
            holderName = "Teste",
            securityCode = "489",
            expirationMonth = "6",
            expirationYear = "2022"
        )

        DirectCheckout.getCardHash(card, object : DirectCheckoutListener<String> {

            override fun onSuccess(cardHash: String) {
                /* Sucesso - A variável cardHash conterá o hash do cartão de crédito */
            }

            override fun onFailure(exception: DirectCheckoutException) {
                /* Erro - A variável exception conterá o erro ocorrido ao obter o hash */
            }
        })
    }
}
