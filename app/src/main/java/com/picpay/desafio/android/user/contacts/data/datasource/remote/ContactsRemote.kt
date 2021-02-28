package com.picpay.desafio.android.user.contacts.data.datasource.remote

import com.picpay.desafio.android.common.model.NetworkingResponse
import com.picpay.desafio.android.user.contacts.data.datasource.remote.model.UserResponse

interface ContactsRemote {
    suspend fun fetch(): List<UserResponse>
}
