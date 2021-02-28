package com.picpay.desafio.android.common.cache

import java.util.concurrent.TimeUnit

data class CacheSetup (
    val maxStale: Int,
    val maxScaleTimeUnit: TimeUnit,
    val maxAge: Int,
    val maxAgeTimeUnit: TimeUnit
)
