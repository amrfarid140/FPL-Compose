package me.amryousef.fpl.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import me.amryousef.fpl.LocalServiceProvider

@Composable
fun appContext(): AppStateStore {
    val loginService = LocalServiceProvider.current.loginService
    val stateStore = rememberSaveable(saver = AppStateSaver(loginService)) {
        AppStateStore(loginService)
    }
    return stateStore
}