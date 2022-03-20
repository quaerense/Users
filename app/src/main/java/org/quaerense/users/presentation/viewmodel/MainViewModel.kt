package org.quaerense.users.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.quaerense.users.data.repository.UserRepositoryImpl
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.usecase.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application)

    val getUserUseCase = GetUserUseCase(repository)
    val getUserListUseCase = GetUserListUseCase(repository)
    private val editUserUseCase = EditUserUseCase(repository)
    private val deleteUserUseCase = DeleteUserUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.Main)

    fun deleteUser(user: User) {
        scope.launch {
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