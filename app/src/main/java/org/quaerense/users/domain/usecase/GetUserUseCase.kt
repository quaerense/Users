package org.quaerense.users.domain.usecase

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    operator fun invoke(id: Int): LiveData<User> {
        return repository.get(id)
    }
}