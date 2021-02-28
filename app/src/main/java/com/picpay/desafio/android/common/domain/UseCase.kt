package com.picpay.desafio.android.common.domain


import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.model.NetworkingError
import retrofit2.HttpException
import java.net.HttpURLConnection

abstract class UseCase<Input, Output> {
    abstract suspend fun execute(vararg params: Param<Input>): State<Output>

    fun mapToStateFailure(exception: Exception): State.Failure {
        return when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> State.Failure(GatewayTimeout())
                    else -> State.Failure(GatewayTimeout())
                }
            }
            else -> State.Failure(
                Error.Local(
                    "",
                    R.string.generic_error_title,
                    R.string.generic_error_message
                )
            )
        }
    }
}
