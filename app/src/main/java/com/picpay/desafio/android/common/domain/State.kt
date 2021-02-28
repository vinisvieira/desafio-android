package com.picpay.desafio.android.common.domain

sealed class State<out T> {
    data class Success<T>(val data: T) : State<T>()
    data class Failure(val error: Error) : State<Nothing>()
}
