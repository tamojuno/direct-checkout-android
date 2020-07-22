package br.com.juno.directcheckout.api

import org.json.JSONObject

internal class JunoApi(private val httpClient: HttpClient) {

    fun getPublicKey(publicToken:String, version:String) : ApiResponse {

        val result = httpClient.post("get-public-encryption-key.json",
            mapOf(
                "publicToken" to publicToken,
                "version" to version
            )
        )
        return ApiResponse(JSONObject(result))
    }

    fun getCardHash(publicToken:String, encryptedData: String) : ApiResponse {

        val result = httpClient.post("get-credit-card-hash.json",
            mapOf(
                "publicToken" to publicToken,
                "encryptedData" to encryptedData
            )
        )
        return ApiResponse(JSONObject(result))
    }
}

internal object ApiFactory {

    fun makeRetrofitService(prodEnvironment: Boolean) : JunoApi {
        return JunoApi(HttpClient(prodEnvironment))
    }
}