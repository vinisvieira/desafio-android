package com.picpay.desafio.android.user.contacts.data.datasource.remote.impl

import com.picpay.desafio.android.common.model.NetworkingResponse
import com.picpay.desafio.android.user.contacts.data.datasource.remote.ContactsRemote
import com.picpay.desafio.android.user.contacts.data.datasource.remote.model.UserResponse
import com.picpay.desafio.android.user.contacts.data.datasource.remote.service.PicPayService

class ContactsRemoteImpl(
    private val picPayService: PicPayService
) : ContactsRemote {
    override suspend fun fetch(): List<UserResponse> =
        picPayService.getUsers()
}
