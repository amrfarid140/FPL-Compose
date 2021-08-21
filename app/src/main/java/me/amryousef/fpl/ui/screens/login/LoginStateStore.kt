package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.amryousef.fpl.data.LoginService

class LoginStateStore(
    initialValue: LoginState? = null,
    private val loginService: LoginService
) {
    val state = mutableStateOf(
        initialValue ?:
        LoginState(
            email = "",
            password = "",
            isPasswordVisible = false,
            isSubmitting = false
        )
    )

    fun onEmailChanged(newEmail: String) {
        state.value = state.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        state.value = state.value.copy(password = newPassword)
    }

    fun onPasswordVisibilityToggled() {
        state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
    }

    suspend fun doLogin() {
        state.value = state.value.copy(isSubmitting = true)
        withContext(Dispatchers.IO) {
            try {
                loginService.login(
                    email = state.value.email,
                    password = state.value.password
                )
                state.value = state.value.copy(isSubmitting = false)
            } catch (exception: Exception) {
                state.value = state.value.copy(isSubmitting = false)
            }
        }
    }
}