package org.quaerense.users.domain.repository

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User

interface UserRepository {

    suspend fun get(id: Int): User

    fun getAll(): LiveData<List<User>>

    suspend fun edit(user: User)

    suspend fun delete(user: User)

    fun loadData()
}