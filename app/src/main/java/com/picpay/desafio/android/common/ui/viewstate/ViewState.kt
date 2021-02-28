package com.picpay.desafio.android.common.ui.viewstate

import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Failure(val error: Error) : ViewState<Nothing>()
    object Done : ViewState<Nothing>()
}
