package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.Composable
import me.amryousef.fpl.LocalServiceProvider
import me.amryousef.fpl.data.LoginService

@Composable
fun useLoginService(): LoginService {
    return LocalServiceProvider.current.loginService
}