package org.quaerense.users.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.usecase.EditUserUseCase
import org.quaerense.users.domain.usecase.GetUserUseCase
import javax.inject.Inject

class UserEditViewModel @Inject constructor(
    application: Application,
    private val editUserUseCase: EditUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : AndroidViewModel(application) {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _errorInputFirstName = MutableLiveData<Boolean>()
    val errorInputFirstName: LiveData<Boolean>
        get() = _errorInputFirstName

    private val _errorInputLastName = MutableLiveData<Boolean>()
    val errorInputLastName: LiveData<Boolean>
        get() = _errorInputLastName

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getUser(id: Int) {
        viewModelScope.launch {
            val user = getUserUseCase(id)
            _user.value = user
        }
    }

    fun editUser(
        inputFirstName: String?,
        inputLastName: String?,
        inputEmail: String?,
        inputImageUri: Uri?
    ) {
        val firstName = parseInput(inputFirstName)
        val lastName = parseInput(inputLastName)
        val email = parseInput(inputEmail)
        val avatarUrl = parseInputImageUri(inputImageUri)
        val fieldsValid = validateInput(firstName, lastName, email)
        val avatarUrlIsValid = validateImageUrl(avatarUrl)
        if (fieldsValid) {
            _user.value?.let {
                viewModelScope.launch {
                    val user = it.copy(firstName = firstName, lastName = lastName, email = email)
                    if (avatarUrlIsValid) {
                        user.avatarUrl = avatarUrl
                    }
                    editUserUseCase(user)
                    finishWork()
                }
            }
        }
    }

    private fun parseInput(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun parseInputImageUri(input: Uri?): String {
        return input.toString()
    }

    private fun validateInput(firstName: String, lastName: String, email: String): Boolean {
        var result = true

        if (firstName.isBlank()) {
            _errorInputFirstName.value = true
            result = false
        }
        if (lastName.isBlank()) {
            _errorInputLastName.value = true
            result = false
        }
        if (email.isBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorInputEmail.value = true
            result = false
        }

        return result
    }

    private fun validateImageUrl(imageUrl: String): Boolean {
        if (imageUrl == "null" || imageUrl.isBlank()) return false

        return true
    }

    fun resetErrorInputFirstName() {
        _errorInputFirstName.value = false
    }

    fun resetErrorInputLastName() {
        _errorInputLastName.value = false
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}