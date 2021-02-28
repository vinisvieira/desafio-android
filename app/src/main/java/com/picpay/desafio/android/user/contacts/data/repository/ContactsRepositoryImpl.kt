package com.picpay.desafio.android.user.contacts.data.repository

import com.picpay.desafio.android.user.contacts.data.datasource.remote.ContactsRemote
import com.picpay.desafio.android.user.contacts.data.extensions.toUserViewDataOutput
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput
import com.picpay.desafio.android.user.contacts.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsRemote: ContactsRemote
) : ContactsRepository {
    override suspend fun fetch(): List<UserViewDataOutput> {
        return contactsRemote.fetch().toUserViewDataOutput()
    }
}
