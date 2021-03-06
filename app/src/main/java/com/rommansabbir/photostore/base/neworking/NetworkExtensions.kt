package com.rommansabbir.photostore.base.neworking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

/*Check if device is connected to the internet or not*/
fun Context.isInternetConnected() = networkInfo?.isConnected ?: false