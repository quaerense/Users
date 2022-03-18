package org.quaerense.users.domain.usecase

import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository

class EditUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(user: User) {
        repository.edit(user)
    }
}