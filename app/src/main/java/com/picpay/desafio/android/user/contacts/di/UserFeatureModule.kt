package com.picpay.desafio.android.user.contacts.di

import com.picpay.desafio.android.common.di.RETROFIT
import com.picpay.desafio.android.user.contacts.data.datasource.remote.ContactsRemote
import com.picpay.desafio.android.user.contacts.data.datasource.remote.impl.ContactsRemoteImpl
import com.picpay.desafio.android.user.contacts.data.datasource.remote.service.PicPayService
import com.picpay.desafio.android.user.contacts.data.repository.ContactsRepositoryImpl
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCase
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCaseImpl
import com.picpay.desafio.android.user.contacts.domain.repository.ContactsRepository
import com.picpay.desafio.android.user.contacts.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

object Constants {
    const val fetchContactsUC = "fetchContactsUC"
}

val userFeatureModule = module {

    single<PicPayService> {
        get<Retrofit>(named(RETROFIT)).create(PicPayService::class.java)
    }

    single<ContactsRemote> { ContactsRemoteImpl(picPayService = get()) }
    single<ContactsRepository> { ContactsRepositoryImpl(contactsRemote = get()) }
    factory<FetchContactsUseCase>(named(Constants.fetchContactsUC)) {
        FetchContactsUseCaseImpl(
            contactsRepository = get()
        )
    }

    viewModel { MainViewModel(fetchContactsUseCase = get(named(Constants.fetchContactsUC))) }
}
