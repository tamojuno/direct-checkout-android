package br.com.juno.directcheckout

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Html
import br.com.juno.directcheckout.api.ApiFactory
import br.com.juno.directcheckout.model.Card
import br.com.juno.directcheckout.model.DirectCheckoutException
import br.com.juno.directcheckout.model.DirectCheckoutListener
import br.com.juno.directcheckout.utils.CardUtils
import br.com.juno.directcheckout.utils.Validate
import br.com.juno.directcheckout.utils.Validate.NO_INITIALIZED
import br.com.juno.directcheckout.utils.encrypt

object DirectCheckout{

    private const val PUBLIC_TOKEN = "br.com.juno.directcheckout.public_token"

    @JvmStatic private lateinit var applicationContext: Context
    @JvmStatic private lateinit var publicToken: String
    @JvmStatic private var prodEnvironment: Boolean = true
    @JvmStatic private var publicKey: String ?= null
    @JvmStatic private var sdkInitialized = false

    /**
     * Initialize DirectCheckout SDK
     * @param context - The application context
     * @param prod - Flag to select environment true to PROD or false to SANDBOX(Tests)
     */
    @JvmStatic
    @JvmOverloads
    fun initialize(context: Context, prod:Boolean = true, listener:DirectCheckoutListener<Boolean>? = null){
        Validate.notNull(context, "")
        Validate.hasInternetPermissions(context)
        prodEnvironment = prod
        applicationContext = context
        loadPublicTokenMetadata()
        loadPublicKey(listener)
        sdkInitialized = true
    }

    /**
     * Get card hash to make security transactions in Juno/BoletoBancario API
     * @param card - Card object to get hash code
     * @param listener - DirectCheckoutListener, a callback called when process finishes with success or fail
     */
    @JvmStatic
    fun getCardHash(card: Card, listener: DirectCheckoutListener<String>){

        if(!sdkInitialized){
            listener.onFailure( DirectCheckoutException(NO_INITIALIZED))
            return
        }

        onPublicKeyDone {
            val service = ApiFactory.makeRetrofitService(prodEnvironment)
            val thread = Thread(Runnable {
                try{
                    val response = service.getCardHash(publicToken, card.encrypt(publicKey?:""))
                    if(response.success){
                        Handler(Looper.getMainLooper()).post {
                            listener.onSuccess(response.data)
                        }
                    }else{
                        Handler(Looper.getMainLooper()).post {
                            listener.onFailure(DirectCheckoutException(getErrorMessage(response.errorMessage)))
                        }
                    }
                }catch (e: Exception){
                    Handler(Looper.getMainLooper()).post {
                        listener.onFailure(DirectCheckoutException(e.message ?: "Network error"))
                    }
                }
            })
            thread.start()
        }
    }

    private fun onPublicKeyDone(action : () -> Unit ){
        if(publicKey == null){
            Handler().postDelayed({ // Do something after 5s = 5000ms
                onPublicKeyDone(action)
            }, 100)
        }else{
            action()
        }
    }

    /**
     * Validate if card number is valid
     * @param cardNumber
     * @return Boolean
     */
    @JvmStatic
    fun isValidCardNumber(cardNumber:String)
            = CardUtils.validateNumber(cardNumber)

    /**
     * Get CardType (Label)
     * @param cardNumber
     * @return Card type - Label
     */
    @JvmStatic
    fun getCardType(cardNumber: String)
            = CardUtils.getCardType(cardNumber)?.name

    /**
     * Validate if security code is correct for card type
     * @param cardNumber
     * @param securityCode
     * @return Boolean
     */
    @JvmStatic
    fun isValidSecurityCode(cardNumber: String, securityCode:String)
            = CardUtils.validateCVC(cardNumber, securityCode)

    /**
     * Validate if expiration date is Valid
     * @param expirationMonth
     * @param expirationYear
     * @return Boolean
     */
    @JvmStatic
    fun isValidExpireDate(expirationMonth: String, expirationYear: String)
            = CardUtils.validateExpireDate(expirationMonth, expirationYear)

    /**
     * Validate all card data
     * @param card - Card object
     * @throws DirectCheckoutException - if some data is invalid
     * @return Boolean
     */
    @JvmStatic
    @Throws(DirectCheckoutException::class)
    fun isValidCardData(card:Card):Boolean {

        if(card.holderName.isEmpty()){
            throw DirectCheckoutException("Invalid holder name")
        }
        if(!CardUtils.validateNumber(card.cardNumber)){
            throw DirectCheckoutException("Invalid card number")
        }

        if(!CardUtils.validateCVC(card.cardNumber, card.securityCode)){
            throw DirectCheckoutException("Invalid security code")
        }

        if(!CardUtils.validateExpireDate(card.expirationMonth, card.expirationYear)){
            throw DirectCheckoutException("Invalid expire date")
        }

        return true
    }

    private fun loadPublicKey(listener:DirectCheckoutListener<Boolean>? = null){
        val service = ApiFactory.makeRetrofitService(prodEnvironment)
        val thread = Thread(Runnable {
            try{
                val response = service.getPublicKey(publicToken, BuildConfig.VERSION_NAME)
                if(response.success){
                    publicKey = response.data
                    Handler(Looper.getMainLooper()).post {
                        listener?.onSuccess(true)
                    }

                }else{
                    Handler(Looper.getMainLooper()).post {
                        listener?.onFailure(DirectCheckoutException(getErrorMessage(response.errorMessage)))
                    }
                    sdkInitialized = false
                }
            }catch (e: Exception){
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    listener?.onFailure(DirectCheckoutException(e.message?: "Network error"))
                }
                sdkInitialized = false
            }
        })
        thread.start()
    }

    private fun loadPublicTokenMetadata(){

        try {
            val ai = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName, PackageManager.GET_META_DATA
            )
            if (!::publicToken.isInitialized) {
                val token = ai.metaData?.get(PUBLIC_TOKEN)
                if (token is String) {
                    publicToken = token
                } else {
                    throw DirectCheckoutException(Validate.NO_PUBLIC_TOKEN)
                }
            }

        } catch (e: Exception) {
            throw DirectCheckoutException(Validate.NO_PUBLIC_TOKEN)
        }
    }

    private fun validateInitialize(){
        if(!sdkInitialized){
            throw DirectCheckoutException(NO_INITIALIZED)
        }
    }

    private fun getErrorMessage(errorMessage: String) : String {
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(errorMessage , Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(errorMessage)
        }.toString()
    }
}
