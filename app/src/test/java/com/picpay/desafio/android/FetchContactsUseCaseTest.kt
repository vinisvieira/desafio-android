package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCase
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCaseImpl
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput
import com.picpay.desafio.android.user.contacts.domain.repository.ContactsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class FetchContactsUseCaseTest {

    @Mock
    private lateinit var contactsRepository: ContactsRepository

    private val fetchContactsUseCase: FetchContactsUseCase by lazy {
        FetchContactsUseCaseImpl(contactsRepository)
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldFetchUseCaseSucceed() = runBlockingTest {
        val expectedResult = emptyList<UserViewDataOutput>()

        whenever(contactsRepository.fetch()).thenReturn(expectedResult)

        val result = fetchContactsUseCase.execute()

        verify(contactsRepository, times(1)).fetch()

        assertEquals(State.Success(expectedResult), result)
    }

}
