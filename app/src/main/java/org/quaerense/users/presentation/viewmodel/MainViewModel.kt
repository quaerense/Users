package org.quaerense.users.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.users.data.repository.UserRepositoryImpl
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.usecase.DeleteUserUseCase
import org.quaerense.users.domain.usecase.EditUserUseCase
import org.quaerense.users.domain.usecase.GetUserListUseCase
import org.quaerense.users.domain.usecase.LoadDataUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application)

    val getUserListUseCase = GetUserListUseCase(repository)
    private val deleteUserUseCase = DeleteUserUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    fun deleteUser(user: User) {
        viewModelScope.launch {
            deleteUserUseCase(user)
        }
    }

    fun loadData() {
        loadDataUseCase()
    }

    init {
        loadData()
    }
}