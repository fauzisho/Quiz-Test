package com.uzi.quiz

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        component = AppComponent(this, BuildConfig.BASE_URL_API)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: App? = null

        @JvmStatic
        val instance: App
            get() {
                if (INSTANCE == null) {
                    synchronized(App::class.java) {
                        if (INSTANCE == null) {
                            throw RuntimeException("instance is null!")
                        }
                    }
                }
                return INSTANCE!!
            }

    }
}