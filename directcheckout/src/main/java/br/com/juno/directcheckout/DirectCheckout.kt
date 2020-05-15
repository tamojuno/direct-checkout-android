package br.com.juno.directcheckout

import android.content.Context
import android.content.pm.PackageManager
import br.com.juno.directcheckout.api.ApiFactory
import br.com.juno.directcheckout.model.Card
import br.com.juno.directcheckout.model.DirectCheckoutException
import br.com.juno.directcheckout.model.DirectCheckoutListener
import br.com.juno.directcheckout.utils.CardUtils
import br.com.juno.directcheckout.utils.Validate
import br.com.juno.directcheckout.utils.Validate.NO_INITIALIZED
import br.com.juno.directcheckout.utils.encrypt
import kotlinx.coroutines.*

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
        validateInitialize()

        //await publicKey load
        while (publicKey == null) runBlocking{
            delay(100)
        }

        val service = ApiFactory.makeRetrofitService(prodEnvironment)
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = service.getCardHash(publicToken, card.encrypt(publicKey?:""))
                if(response.success){
                    withContext(Dispatchers.Main){
                        listener.onSuccess(response.data)
                    }

                }else{
                    withContext(Dispatchers.Main){
                        listener.onFailure(DirectCheckoutException(response.errorMessage))
                    }

                }
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    listener.onFailure(DirectCheckoutException(e.message ?: "Network error"))
                }
            }
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
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val response = service.getPublicKey(publicToken, BuildConfig.VERSION_NAME)
                if(response.success){
                    publicKey = response.data
                    withContext(Dispatchers.Main) {
                        listener?.onSuccess(true)
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        listener?.onFailure(DirectCheckoutException(response.errorMessage))
                    }
                    sdkInitialized = false
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    listener?.onFailure(DirectCheckoutException(e.message?: "Network error"))
                }
                sdkInitialized = false
            }

        }
    }

    private fun loadPublicTokenMetadata(){

        try {
            val ai = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName, PackageManager.GET_META_DATA
            )
            if (!::publicToken.isInitialized) {
                val token = ai?.metaData?.get(PUBLIC_TOKEN)
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
}
