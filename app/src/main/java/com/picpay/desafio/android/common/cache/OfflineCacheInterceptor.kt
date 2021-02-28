package com.picpay.desafio.android.common.cache

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class OfflineCacheInterceptor(
    private val cacheSetup: CacheSetup
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(cacheSetup.maxStale, cacheSetup.maxScaleTimeUnit)
                .maxAge(cacheSetup.maxAge, cacheSetup.maxAgeTimeUnit)
                .build()
            val offlineRequest = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build()
            chain.proceed(offlineRequest)
        }
    }
}
