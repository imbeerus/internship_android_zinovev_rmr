package com.lockwood.core.network.authenticator

import android.content.Context
import android.content.Intent
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class UserToLoginAuthenticator(
    private val context: Context
) : Authenticator {

    companion object {

        private const val LOGIN_ACTIVITY_CLASS_NAME =
            "com.lockwood.themoviedb.login.presentation.ui.LoginActivity"
    }

    // В случае истечения срока request_token или session_id, необходимо открыть экран
    // авторизации для повторного получения токена
    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        openLoginActivity()
        return null
    }

    @Synchronized
    private fun openLoginActivity() {
        val intent = Intent().apply {
            setClassName(context.packageName, LOGIN_ACTIVITY_CLASS_NAME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

}