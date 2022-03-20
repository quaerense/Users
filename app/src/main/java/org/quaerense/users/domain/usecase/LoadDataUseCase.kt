package org.quaerense.users.domain.usecase

import org.quaerense.users.domain.repository.UserRepository

class LoadDataUseCase(private val repository: UserRepository) {

    operator fun invoke() {
        repository.loadData()
    }
}