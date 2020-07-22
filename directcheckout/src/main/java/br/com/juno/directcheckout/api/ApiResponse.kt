package br.com.juno.directcheckout.api

import org.json.JSONObject
import java.lang.Exception

internal class ApiResponse(
    val success: Boolean,
    val errorMessage: String,
    val data: String
){

    constructor(jsonObject: JSONObject)
            : this(
        success = jsonObject.getSafeBoolean("success"),
        errorMessage = jsonObject.getSafeString("errorMessage"),
        data = jsonObject.getSafeString("data")
    )
}


fun JSONObject.getSafeString(name:String) : String {
    return try {
        getString(name)
    }catch (e:Exception){
        ""
    }
}

fun JSONObject.getSafeBoolean(name:String) : Boolean {
    return try {
        getBoolean(name)
    }catch (e:Exception){
        false
    }
}