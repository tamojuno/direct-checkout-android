package br.com.juno.directcheckout.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import br.com.juno.directcheckout.DirectCheckout;
import br.com.juno.directcheckout.model.Card;
import br.com.juno.directcheckout.model.DirectCheckoutException;
import br.com.juno.directcheckout.model.DirectCheckoutListener;
import org.jetbrains.annotations.NotNull;

public class TesteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Card card  = new Card(
                "5191 8655 5738 6780",
                "Teste",
               "489",
                "6",
                "2022"
        );


        DirectCheckout.getCardHash(card, new DirectCheckoutListener<String>() {
            @Override
            public void onSuccess(@NotNull String cardHash) {
                Log.i("Aqui", cardHash);
            }

            @Override
            public void onFailure(@NotNull DirectCheckoutException exception) {
                Log.i("error", exception.getMessage());
            }
        });
    }
}
