package br.com.juno.directcheckout.utils

import android.util.Base64
import android.util.Log
import br.com.juno.directcheckout.model.Card
import br.com.juno.directcheckout.model.toMap
import org.json.JSONObject
import org.json.JSONStringer
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

internal object Crypto{
    const val CRYPTO_METHOD = "RSA"
    const val MD_NAME = "SHA-256"
    const val MG_NAME = "MGF1"
    const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPPadding"
}

internal fun Card.encrypt(key:String):String{
    return JSONObject(toMap()).toString().encrypt(key)
}

internal fun String.encrypt(key: String):String{
    val pubKey = key.toPublicKey()
    val parameterSpec =  OAEPParameterSpec(
        Crypto.MD_NAME,
        Crypto.MG_NAME, MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT)
    val cipher: Cipher = Cipher.getInstance(Crypto.CRYPTO_TRANSFORM)
    cipher.init(Cipher.ENCRYPT_MODE, pubKey, parameterSpec)
    val encryptedBytes = cipher.doFinal(this.toByteArray(Charsets.UTF_8))
    return Base64.encodeToString(encryptedBytes,Base64.NO_WRAP)
}

internal fun String.toPublicKey():PublicKey{
    val keyBytes: ByteArray = Base64.decode( this, Base64.DEFAULT)
    val spec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance(Crypto.CRYPTO_METHOD)
    return keyFactory.generatePublic(spec)
}

