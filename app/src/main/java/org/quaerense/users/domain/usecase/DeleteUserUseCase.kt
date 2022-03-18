package org.quaerense.users.domain.usecase

import org.quaerense.users.domain.repository.UserRepository

class DeleteUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}