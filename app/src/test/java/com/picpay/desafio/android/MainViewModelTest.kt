package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.State
import com.picpay.desafio.android.common.ui.viewstate.ViewState
import com.picpay.desafio.android.user.contacts.domain.FetchContactsUseCase
import com.picpay.desafio.android.user.contacts.domain.model.UserViewDataOutput
import com.picpay.desafio.android.user.contacts.ui.extensions.toUserViewData
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData
import com.picpay.desafio.android.user.contacts.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var fetchContactsUseCase: FetchContactsUseCase

    @Mock
    private lateinit var observer: Observer<ViewState<List<UserViewData>>>

    private val viewModel: MainViewModel by lazy {
        MainViewModel(fetchContactsUseCase, testCoroutineDispatcher)
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun shouldFetchSucceed() = runBlockingTest {
        val expectedResult = emptyList<UserViewDataOutput>()

        whenever(fetchContactsUseCase.execute()).thenReturn(State.Success(expectedResult))

        viewModel.fetchContactsLiveData.observeForever(observer)
        viewModel.fetch()

        verify(fetchContactsUseCase, times(1)).execute()

        verify(observer, times(1)).onChanged(ViewState.Loading)
        verify(observer, times(1)).onChanged(ViewState.Success(expectedResult.toUserViewData()))
        verify(observer, times(0)).onChanged(
            ViewState.Failure(
                Error.Local(
                    "",
                    R.string.generic_error_message,
                    R.string.generic_error_title
                )
            )
        )
        verify(observer, times(1)).onChanged(ViewState.Done)
    }

    @Test
    fun shouldFetchFail() = runBlockingTest {
        val expectedResult =
            Error.Local("", R.string.generic_error_message, R.string.generic_error_title)

        whenever(fetchContactsUseCase.execute()).thenReturn(State.Failure(expectedResult))

        viewModel.fetchContactsLiveData.observeForever(observer)
        viewModel.fetch()

        verify(fetchContactsUseCase, times(1)).execute()
        verify(observer, times(1)).onChanged(ViewState.Loading)
        verify(observer, times(1)).onChanged(ViewState.Failure(expectedResult))
        verify(observer, times(1)).onChanged(ViewState.Done)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
