package org.quaerense.users.domain.usecase

import androidx.lifecycle.LiveData
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserRepository) {

    operator fun invoke(): LiveData<List<User>> {
        return repository.getAll()
    }
}