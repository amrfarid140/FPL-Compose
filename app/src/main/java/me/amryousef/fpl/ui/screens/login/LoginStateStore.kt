package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.amryousef.fpl.data.LoginService

class LoginStateStore(
    initialValue: LoginState? = null,
    private val loginService: LoginService,
    private val coroutineScope: CoroutineScope,
    private val onLoginComplete: () -> Unit
) {

    constructor(
        loginService: LoginService,
        coroutineScope: CoroutineScope,
        onLoginComplete: () -> Unit
    ) : this(null, loginService, coroutineScope, onLoginComplete)

    val state = mutableStateOf(
        initialValue ?: LoginState(
            email = "",
            password = "",
            isPasswordVisible = false,
            isSubmitting = false,
            shouldDisplayErrorAlert = false
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

    fun onErrorAlertDismissed() {
        state.value = state.value.copy(shouldDisplayErrorAlert = !state.value.shouldDisplayErrorAlert)
    }

    fun doLogin() {
        state.value = state.value.copy(isSubmitting = true)
        coroutineScope.launch(Dispatchers.IO) {
            val result = loginService.login(
                email = state.value.email,
                password = state.value.password
            )
            state.value = state.value.copy(
                isSubmitting = false,
                shouldDisplayErrorAlert = result.isFailure
            )
            if (result.isSuccess) {
                onLoginComplete()
            }
        }
    }
}