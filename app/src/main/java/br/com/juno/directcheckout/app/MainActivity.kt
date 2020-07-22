package br.com.juno.directcheckout.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.juno.directcheckout.DirectCheckout
import br.com.juno.directcheckout.model.Card
import br.com.juno.directcheckout.model.DirectCheckoutException
import br.com.juno.directcheckout.model.DirectCheckoutListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCardHash.setOnClickListener {
            getCardHash()
        }

    }

    private fun getCardHash(){
        val card = Card(
            cardNumber = "5191865557386780",
            holderName = "Teste",
            securityCode = "489",
            expirationMonth = "6",
            expirationYear = "2022"
        )

        DirectCheckout.getCardHash(card, object : DirectCheckoutListener<String> {

            override fun onSuccess(result: String) {
                cardHash.text = result
            }

            override fun onFailure(exception: DirectCheckoutException) {
                cardHash.text = exception.message
            }
        })
    }
}
