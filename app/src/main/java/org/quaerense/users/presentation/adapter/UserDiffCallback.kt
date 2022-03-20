package org.quaerense.users.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import org.quaerense.users.domain.model.User

object UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}