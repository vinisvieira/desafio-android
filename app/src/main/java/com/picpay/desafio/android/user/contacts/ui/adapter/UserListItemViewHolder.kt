package com.picpay.desafio.android.user.contacts.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val viewBinding: ListItemUserBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(userView: UserViewData) {
        when {
            userView.name.isNotEmpty() -> viewBinding.name.text = userView.name
            else -> viewBinding.name.text = ""
        }

        when {
            userView.username.isNotEmpty() -> viewBinding.username.text = userView.username
            else -> viewBinding.username.text = ""
        }

        viewBinding.progressBar.visibility = View.VISIBLE

        when {
            userView.img.isNotEmpty() -> {
                Picasso.get()
                    .load(userView.img)
                    .error(R.drawable.ic_round_account_circle)
                    .into(viewBinding.picture, object : Callback {
                        override fun onSuccess() {
                            viewBinding.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            viewBinding.progressBar.visibility = View.GONE
                        }
                    })
            }
            else -> {
                viewBinding.picture.setImageResource(R.drawable.ic_round_account_circle)
                viewBinding.progressBar.visibility = View.GONE
            }
        }

    }
}
