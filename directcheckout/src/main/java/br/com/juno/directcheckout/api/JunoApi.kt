package br.com.juno.directcheckout.api

import br.com.juno.directcheckout.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import okhttp3.OkHttpClient

internal interface JunoApi {

    @FormUrlEncoded
    @POST("get-public-encryption-key.json")
    suspend fun getPublicKey(
        @Field("publicToken") publicToken:String,
        @Field("version") version:String
    ) : ApiResponse

    @FormUrlEncoded
    @POST("get-credit-card-hash.json")
    suspend fun getCardHash(
        @Field("publicToken") publicToken:String,
        @Field("encryptedData") encryptedData:String
    ):ApiResponse
}

internal object ApiFactory {

    fun makeRetrofitService(prodEnvironment: Boolean): JunoApi {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.PROD.takeIf { prodEnvironment }?: BuildConfig.SANDBOX)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(JunoApi::class.java)
    }
}