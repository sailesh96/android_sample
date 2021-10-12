package com.example.sample

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel:ViewModel() {
    @ExperimentalCoroutinesApi
    private val password = MutableStateFlow("")
    @ExperimentalCoroutinesApi
    private val username = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    fun setPassword(password: String) {
        this.password.value = password
    }

    @ExperimentalCoroutinesApi
    fun setUserName(username: String) {
        this.username.value = username
    }

    @ExperimentalCoroutinesApi
    val isSubmitEnabled: Flow<ValidationMessage> = combine(username, password) { username, password ->
        val usernameRegex = "[a-zA-Z0-9]+"
        val passwordRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!?_])([a-zA-Z0-9@!?_]{5,})"
        val isUsernameCorrect = username.matches(usernameRegex.toRegex())
        val isPasswordCorrect = password.matches(passwordRegex.toRegex())
        return@combine ValidationMessage(isUsernameCorrect,isPasswordCorrect)
    }

}

data class ValidationMessage(
    val username:Boolean=false,
    val password:Boolean=false
)
