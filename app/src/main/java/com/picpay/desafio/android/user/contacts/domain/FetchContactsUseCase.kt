package com.picpay.desafio.android.user.contacts.domain

import com.picpay.desafio.android.common.domain.Param
import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.common.domain.UseCase
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput
import com.picpay.desafio.android.user.contacts.domain.repository.ContactsRepository

typealias FetchContactsUseCase = UseCase<Nothing, List<UserViewDataOutput>>

class FetchContactsUseCaseImpl(
    private val contactsRepository: ContactsRepository
) : FetchContactsUseCase() {
    override suspend fun execute(vararg params: Param<Nothing>): State<List<UserViewDataOutput>> {
        return try {
            val users = contactsRepository.fetch()
            State.Success(users)
        } catch (e: Exception) {
            mapToStateFailure(e)
        }
    }
}
