package br.com.juno.directcheckout.model

interface DirectCheckoutListener<T>{

    fun onSuccess(result: T)

    fun onFailure(exception:DirectCheckoutException)
}