package org.quaerense.users.domain.usecase

import org.quaerense.users.domain.repository.UserRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: UserRepository) {

    operator fun invoke() {
        repository.loadData()
    }
}