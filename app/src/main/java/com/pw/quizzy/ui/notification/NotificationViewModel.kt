package com.pw.quizzy.ui.notification

import android.content.Context
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider


class NotificationViewModel : ViewModel() {

    fun logout(context: Context, auth0: Auth0, onLogoutComplete: () -> Unit) {
        WebAuthProvider.logout(auth0)
            .withScheme("com.pw.quizzy")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onLogoutComplete()
                }

                override fun onSuccess(result: Void?) {
                    onLogoutComplete()
                }
            })
    }
}