package com.picpay.desafio.android.user.contacts.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData

class UserListAdapter :
    ListAdapter<UserViewData, UserListItemViewHolder>(UserListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
