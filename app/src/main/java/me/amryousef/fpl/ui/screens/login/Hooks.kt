package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import me.amryousef.fpl.LocalServiceProvider
import me.amryousef.fpl.data.LoginService

@Composable
fun useLoginService(): LoginService {
    return LocalServiceProvider.current.loginService
}

@Composable
fun loginContext(
    onLoginComplete: () -> Unit
): LoginStateStore {
    val loginService = useLoginService()
    val scope = rememberCoroutineScope()
    val loginSaver = LoginStateSaver(loginService, scope, onLoginComplete)
    val loginStateStore = LoginStateStore(loginService, scope, onLoginComplete)
    return rememberSaveable(saver = loginSaver) {
        loginStateStore
    }
}