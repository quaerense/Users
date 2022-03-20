package org.quaerense.users.domain.usecase

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(id: Int): User {
        return repository.get(id)
    }
}