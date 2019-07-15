package br.com.juno.directcheckout.app

import android.app.Application
import br.com.juno.directcheckout.DirectCheckout

class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        DirectCheckout.initialize(this, false)
    }
}