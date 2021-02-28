package com.picpay.desafio.android.common.di

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.common.cache.CacheSetup
import com.picpay.desafio.android.common.cache.NetworkCacheInterceptor
import com.picpay.desafio.android.common.cache.OfflineCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

const val RETROFIT = "retrofit"
const val CLIENT = "client"
const val GSON = "gson"
const val CACHE = "cache"
const val MAX_SIZE: Long = 10 * 1024 * 1024
const val CACHE_DIR_NAME = "offline-cache"


val networkingModule = module {

    single(named(GSON)) { GsonBuilder().create() }
    factory { (cacheSetup: CacheSetup) ->
        OfflineCacheInterceptor(cacheSetup)
    }
    factory { (cacheSetup: CacheSetup) ->
       NetworkCacheInterceptor(cacheSetup)
    }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single(named(CACHE)) {
        Cache(File(androidApplication().cacheDir, CACHE_DIR_NAME), MAX_SIZE)
    }

    single(named(CLIENT)) {
        val cacheSetup = CacheSetup(10, TimeUnit.MINUTES, 10, TimeUnit.MINUTES)
        OkHttpClient.Builder()
            .cache(get(named(CACHE)))
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addNetworkInterceptor(get<NetworkCacheInterceptor> { parametersOf(cacheSetup) })
            .addInterceptor(get<OfflineCacheInterceptor> { parametersOf(cacheSetup) })
            .build()
    }

    single(named(RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named(CLIENT)))
            .addConverterFactory(GsonConverterFactory.create(get(named(GSON))))
            .build()
    }
}
