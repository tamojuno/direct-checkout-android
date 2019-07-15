package br.com.juno.directcheckout.api

import com.squareup.moshi.Json

internal class ApiResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "errorMessage") val errorMessage: String,
    @Json(name = "data") val data: String
)
