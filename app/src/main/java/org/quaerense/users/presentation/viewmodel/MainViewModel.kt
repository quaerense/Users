package org.quaerense.users.presentation.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.usecase.DeleteUserUseCase
import org.quaerense.users.domain.usecase.GetUserListUseCase
import org.quaerense.users.domain.usecase.LoadDataUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    getUserListUseCase: GetUserListUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : AndroidViewModel(application) {

    val userList = getUserListUseCase()

    fun deleteUser(user: User) {
        viewModelScope.launch {
            deleteUserUseCase(user)
        }
    }

    fun loadData() {
        loadDataUseCase()
    }

    private fun loadDataIfFirstAppLaunch() {
        val preferences = getApplication<Application>().getSharedPreferences(
            APP_PREFERENCES,
            Activity.MODE_PRIVATE
        )
        val isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            val editor = preferences.edit()
            editor.putBoolean(FIRST_LAUNCH, false)
            editor.apply()
            loadData()
        }
    }

    init {
        loadDataIfFirstAppLaunch()
    }

    companion object {

        private const val APP_PREFERENCES = "preferences"
        private const val FIRST_LAUNCH = "first launch"
    }
}