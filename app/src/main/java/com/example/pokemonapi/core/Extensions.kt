package com.example.pokemonapi.core

import android.app.Activity
import android.content.Context
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import android.net.NetworkCapabilities
import android.widget.Toast


class Extensions {
    companion object {
        // 3.- Simplificar toast
        fun Activity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, text, duration).show()
        }

        fun agregarCerosIzquierda(num: Int, size: Int): String {
            return (Math.pow(10.0, size.toDouble()) + num).toInt().toString().substring(1)
        }

        fun isNetworkConnected(activity: Activity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager!!.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        // Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        // Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        // Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }
    }
}