package com.pw.quizzy.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(context: Context, auth0: Auth0) {
        _loginState.value = LoginState.Loading

        WebAuthProvider.login(auth0)
            .withScheme("com.pw.quizzy")
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    viewModelScope.launch {
                        _loginState.value = LoginState.Error(error.message ?: "Login failed")
                    }
                }

                override fun onSuccess(result: Credentials) {
                    viewModelScope.launch {
                        _loginState.value = LoginState.Success
                    }
                }
            })
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}