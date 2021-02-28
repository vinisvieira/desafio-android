package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.common.di.networkingModule
import com.picpay.desafio.android.user.contacts.di.userFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startModules()
    }

    private fun startModules() {
        startKoin {
            androidContext(this@ProjectApplication)
            modules(networkingModule, userFeatureModule)
        }
    }
}
