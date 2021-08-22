package me.amryousef.fpl.ui.screens.main

import androidx.compose.runtime.mutableStateOf
import me.amryousef.fpl.data.LoginService

class AppStateStore(
    initialState: AppState? = null,
    loginService: LoginService
) {

    constructor(loginService: LoginService) : this(null, loginService)

    val state = mutableStateOf(
        initialState ?: AppState(
            isLoggedIn = loginService.isLoggedIn()
        )
    )

    fun onLoginComplete() {
        state.value = state.value.copy(isLoggedIn = true)
    }
}