package com.uzi.quiz.remote

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.uzi.quiz.BuildConfig
import id.com.common.data.remote.exception.ErrorException
import id.com.common.data.remote.exception.ErrorInvalidUserException
import id.com.common.data.remote.exception.ErrorNotConnectException
import id.com.common.data.remote.exception.ErrorTimeoutException
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object HttpClientFactory {
    private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
    private const val CACHE_RESPONSE = "CACHE_RESPONSE"
    private const val MAX_STALE = 60 * 60 * 24 // 1 day
    private const val TIMEOUT = 60

    fun createOkHttpClient(cache: SharedPreferences, context: Context, isDebug: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
//            .addInterceptor { createHeadersInterceptor(it, cache) }
            .addInterceptor(createErrorInterceptor(context))
            .cache(createCache(context))
            .addInterceptor(createCacheInterceptor(context))
            .addInterceptor(createLoggingInterceptor(isDebug))
            .build()
    }

    private fun createCache(context: Context): Cache {
        val httpCacheDir = File(context.cacheDir, CACHE_RESPONSE)
        return Cache(httpCacheDir, CACHE_SIZE.toLong())
    }


    private fun createErrorInterceptor(context: Context): Interceptor {

        return Interceptor {
            if (!isNetworkAvailable(context)) throw ErrorNotConnectException()

            val response = it.proceed(it.request())

            try {
                if (!response.isSuccessful) {
                    throw ErrorException(response.code, parseErrorResponse(response.body!!.string()))
                }

            } catch (e: Exception) {
                if (BuildConfig.DEBUG) e.printStackTrace()
                if (e is UnknownHostException) throw ErrorNotConnectException()
                if (e is SocketTimeoutException) throw ErrorTimeoutException()
                if (e is ErrorInvalidUserException) throw ErrorInvalidUserException()
                if (e is ErrorException && e.code > -1)
                    throw ErrorException(e.message!!)
                throw ErrorException()
            }

            response
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun createCacheInterceptor(context: Context): Interceptor {
        return Interceptor {
            var request = it.request()
            if (!isNetworkAvailable(context)) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE")
                    .build()
                val response = it.proceed(request)

                if (response.cacheResponse != null) {
                    return@Interceptor response
                }
            }
            it.proceed(request)
        }
    }


    private fun createLoggingInterceptor(isDebug: Boolean): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    private fun parseErrorResponse(string: String): String {
        val jsonObject = Gson().fromJson(string, JsonObject::class.java)
        if (jsonObject.has("error")) {
            val jsonError = jsonObject.getAsJsonObject("error")
            if (jsonError.has("errors")) {
                return jsonError.getAsJsonArray("errors").get(0).asString
            }
        }
        return "Unknown Error!"
    }
}