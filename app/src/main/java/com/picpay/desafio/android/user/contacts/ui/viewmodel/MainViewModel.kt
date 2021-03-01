package com.picpay.desafio.android.user.contacts.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.common.ui.viewstate.ViewState
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCase
import com.picpay.desafio.android.user.contacts.ui.extensions.toUserViewData
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val fetchContactsUseCase: FetchContactsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val fetchContactsLiveData = MutableLiveData<ViewState<List<UserViewData>>>()

    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            fetchContactsLiveData.value = ViewState.Loading
            val result = withContext(coroutineDispatcher) {
                fetchContactsUseCase.execute()
            }
            fetchContactsLiveData.value = when (result) {
                is State.Success -> ViewState.Success(result.data.toUserViewData())
                is State.Failure -> ViewState.Failure(result.error)
            }
            fetchContactsLiveData.value = ViewState.Done
        }
    }
}
