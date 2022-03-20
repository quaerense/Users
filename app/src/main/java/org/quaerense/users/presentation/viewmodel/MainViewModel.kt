package org.quaerense.users.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.quaerense.users.data.repository.UserRepositoryImpl
import org.quaerense.users.domain.usecase.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application)

    val getUserUseCase = GetUserUseCase(repository)
    val getUserListUseCase = GetUserListUseCase(repository)
    private val editUserUseCase = EditUserUseCase(repository)
    private val deleteUserUseCase = DeleteUserUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    init {
        loadDataUseCase()
    }
}