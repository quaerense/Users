package org.quaerense.users.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import org.quaerense.users.R
import org.quaerense.users.databinding.ItemUserBinding
import org.quaerense.users.domain.model.User

class UserListAdapter : ListAdapter<User, UserViewHolder>(UserDiffCallback) {
    var onUserClickListener: ((user: User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        Picasso.get()
            .load(user.avatarUrl)
            .error(R.drawable.ic_user_icon)
            .resize(150, 150)
            .centerCrop()
            .into(holder.binding.ivAvatar)
        holder.binding.user = user
        holder.binding.root.setOnClickListener {
            onUserClickListener?.invoke(user)
        }
    }
}