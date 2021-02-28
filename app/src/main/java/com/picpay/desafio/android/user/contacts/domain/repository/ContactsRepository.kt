package com.picpay.desafio.android.user.contacts.domain.repository

import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput

interface ContactsRepository {
    suspend fun fetch(): List<UserViewDataOutput>
}
