package com.picpay.desafio.android.common.model

sealed class NetworkingResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : NetworkingResponse<T>()
    data class Failure(val error: NetworkingError) : NetworkingResponse<Nothing>()
}
