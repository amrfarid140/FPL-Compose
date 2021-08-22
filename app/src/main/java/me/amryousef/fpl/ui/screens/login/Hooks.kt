package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import me.amryousef.fpl.LocalServiceProvider
import me.amryousef.fpl.data.LoginService

@Composable
fun useLoginService(): LoginService {
    return LocalServiceProvider.current.loginService
}

@Composable
fun loginContext(): LoginStateStore {
    val loginService = useLoginService()
    val scope = rememberCoroutineScope()
    val loginSaver = LoginStateSaver(loginService, scope)
    val loginStateStore = LoginStateStore(loginService, scope)
    return rememberSaveable(saver = loginSaver) {
        loginStateStore
    }
}