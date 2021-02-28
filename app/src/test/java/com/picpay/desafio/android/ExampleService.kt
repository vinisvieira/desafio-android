package com.picpay.desafio.android

import com.picpay.desafio.android.user.contacts.data.datasource.remote.service.PicPayService
import com.picpay.desafio.android.user.contacts.data.datasource.remote.model.UserResponse

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<UserResponse> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}