package com.picpay.desafio.android.user.contacts.ui.extensions

import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData

fun List<UserViewDataOutput>.toUserViewData(): List<UserViewData> =
    this.map {
        UserViewData(it.img, it.name, it.id, it.username)
    }
