package org.quaerense.users.domain.repository

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User

interface UserRepository {

    fun get(id: Int): LiveData<User>

    fun getAll(): LiveData<List<User>>

    suspend fun edit(user: User)

    suspend fun delete(id: Int)
}