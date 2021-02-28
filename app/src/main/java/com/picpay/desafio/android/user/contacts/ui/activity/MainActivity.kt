package com.picpay.desafio.android.user.contacts.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.ui.viewstate.ViewState
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.user.contacts.ui.adapter.UserListAdapter
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData
import com.picpay.desafio.android.user.contacts.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter()
    }
    private val viewbiding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewbiding.root)
        setupAdapter()
        setupObserver()
        lifecycleScope.launchWhenResumed { viewModel.fetch() }
    }

    private fun setupObserver() {
        viewModel.fetchContactsLiveData.observe(this,
            Observer { viewState ->
                when (viewState) {
                    is ViewState.Loading -> handlerLoading()
                    is ViewState.Success -> handlerSuccess(viewState.data)
                    is ViewState.Failure -> handlerFailure(viewState.error)
                    is ViewState.Done -> handlerDone()
                }
            })
    }

    private fun handlerDone() {
        viewbiding.userListProgressBar.visibility = View.GONE
    }

    private fun handlerFailure(error: Error) {
        viewbiding.recyclerView.visibility = View.GONE
        viewbiding.userListProgressBar.visibility = View.GONE
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    private fun handlerSuccess(users: List<UserViewData>) {
        viewbiding.recyclerView.visibility = View.VISIBLE
        userListAdapter.submitList(users)
    }

    private fun handlerLoading() {
        viewbiding.userListProgressBar.visibility = View.VISIBLE
    }

    private fun setupAdapter() {
        viewbiding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        viewbiding.recyclerView.adapter = userListAdapter
    }

}
