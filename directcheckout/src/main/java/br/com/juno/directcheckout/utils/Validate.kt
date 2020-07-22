package br.com.juno.directcheckout.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

internal object Validate{

    private const val NO_INTERNET_PERMISSION_REASON = "No internet permissions granted for the app, please add " +
             "<uses-permission android:name=\"android.permission.INTERNET\" /> " +
             "to your AndroidManifest.xml."

    const val NO_PUBLIC_TOKEN = "No public_token setting in the manifest, please add " +
            "<meta-data android:name=\"br.com.juno.directcheckout.public_token\" " +
             "android:value=\"YOUR_PUBLIC_TOKEN\"/> " +
            "to your AndroidManifest.xml."

    const val NO_INITIALIZED = "DirectCheckout was not initialized, please initialize calling  DirectCheckout.initialize(context);"

    fun notNull(arg: Any?, name: String) {
        if (arg == null) {
            throw NullPointerException("Argument '$name' cannot be null")
        }
    }

    fun hasInternetPermissions(context: Context) {
        notNull(context, "context")
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            throw IllegalStateException(NO_INTERNET_PERMISSION_REASON)
        }
    }
}