package com.picpay.desafio.android.user.contacts.data.extensions

import com.picpay.desafio.android.user.contacts.data.datasource.remote.model.UserResponse
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput


fun List<UserResponse>.toUserViewDataOutput(): List<UserViewDataOutput> =
    this.map {
        UserViewDataOutput(it.img ?: "", it.name ?: "", it.id ?: 0, it.username ?: "")
    }
