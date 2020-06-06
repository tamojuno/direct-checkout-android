package br.com.juno.directcheckout.api

import br.com.juno.directcheckout.BuildConfig
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class HttpClient(private val prodEnvironment: Boolean) {

    fun post(path: String, params: Map<String, String>): String {

        lateinit var inputStream: InputStream
        var response: String? = null

        val baseUrl = BuildConfig.PROD.takeIf { prodEnvironment }?: BuildConfig.SANDBOX
        val url = URL("$baseUrl$path")
        val urlConn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        urlConn.apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
            doOutput = true
        }.connect()

        BufferedWriter(OutputStreamWriter(urlConn.outputStream)).apply {
            write(urlEncodeUTF8(params))
            flush()
            close()
        }

        inputStream = if (urlConn.responseCode == HttpURLConnection.HTTP_OK) {
            urlConn.inputStream
        } else {
            urlConn.errorStream
        }

        val reader = BufferedReader(
            InputStreamReader(
                inputStream, "UTF-8"
            ), 8
        )

        val sb = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            sb.append("""$line""".trimIndent())
        }

        inputStream.close()
        response = sb.toString()

        return response
    }

    private fun urlEncodeUTF8(map: Map<String,String>): String {
        val sb = java.lang.StringBuilder()
        for ((key, value) in map) {
            if (sb.isNotEmpty()) {
                sb.append("&")
            }
            sb.append(
                String.format(
                    "%s=%s",
                    urlEncodeUTF8(key),
                    urlEncodeUTF8(value)
                )
            )
        }
        return sb.toString()
    }

    private fun urlEncodeUTF8(s: String?): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedOperationException(e)
        }
    }
}