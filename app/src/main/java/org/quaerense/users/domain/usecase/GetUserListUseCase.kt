package org.quaerense.users.domain.usecase

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository

class GetUserListUseCase(private val repository: UserRepository) {

    operator fun invoke(): LiveData<List<User>> {
        return repository.getAll()
    }
}