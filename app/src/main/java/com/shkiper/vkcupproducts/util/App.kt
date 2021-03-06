package com.shkiper.vkcupproducts.util
import android.app.Application
import com.shkiper.vkcupproducts.ui.login.LoginActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            LoginActivity.startFrom(this@App)
        }
    }
}