package com.picpay.desafio.android.user.contacts.data.datasource.remote.service

import com.picpay.desafio.android.common.model.NetworkingError
import com.picpay.desafio.android.common.model.NetworkingResponse
import com.picpay.desafio.android.user.contacts.data.datasource.remote.model.UserResponse
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
