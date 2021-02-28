package com.picpay.desafio.android.user.contacts.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.user.contacts.ui.model.UserViewData

class UserListDiffCallback : DiffUtil.ItemCallback<UserViewData>() {

    override fun areItemsTheSame(oldItem: UserViewData, newItem: UserViewData): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: UserViewData, newItem: UserViewData): Boolean =
        oldItem == newItem
}
