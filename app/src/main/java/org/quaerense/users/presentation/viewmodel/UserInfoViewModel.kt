package org.quaerense.users.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.users.data.repository.UserRepositoryImpl
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.usecase.GetUserUseCase

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(application)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val getUserUseCase = GetUserUseCase(repository)

    fun getUser(id: Int) {
        viewModelScope.launch {
            val user = getUserUseCase(id)
            _user.value = user
        }
    }
}