package com.picpay.desafio.android

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.common.di.CLIENT
import com.picpay.desafio.android.common.di.GSON
import com.picpay.desafio.android.common.di.RETROFIT
import com.picpay.desafio.android.user.contacts.ui.activity.MainActivity
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4ClassRunner::class)
@KoinApiExtension
class MainActivityTest : KoinComponent {

    private val server = MockWebServer()
    private val url = server.url("/")
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        val networkModuleTest = module {
            single(named(CLIENT), override = true) { OkHttpClient.Builder().build() }
            single(named(RETROFIT), override = true) {
                Retrofit.Builder()
                    .client(get(named(CLIENT)))
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(get(named(GSON))))
                    .build()
            }
        }
        getKoin().loadModules(listOf(networkModuleTest))
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)
            moveToState(Lifecycle.State.RESUMED)
            onView(withText(expectedTitle)).check(matches(isDisplayed()))
            close()
        }
    }

    @Test
    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(ViewMatchers.isCompletelyDisplayed()))
            onView(ViewMatchers.withId(R.id.recyclerView)).check { view, noViewFoundException ->
                if (noViewFoundException != null)
                    throw noViewFoundException

                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                ViewMatchers.assertThat(adapter?.itemCount, Matchers.`is`(1))
            }
            close()
        }
    }

    @Test
    fun shouldHideContentAndShowError() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> errorResponse
                    else -> errorResponse
                }
            }
        }

        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
            onView(ViewMatchers.withId(R.id.recyclerView)).check { view, noViewFoundException ->
                if (noViewFoundException != null)
                    throw noViewFoundException

                val recyclerView = view as RecyclerView
                val visibility = recyclerView.visibility
                ViewMatchers.assertThat(visibility, Matchers.`is`(View.GONE))
            }
            close()
        }
    }

    companion object {
        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}
