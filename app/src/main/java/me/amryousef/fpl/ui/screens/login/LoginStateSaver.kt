package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import kotlinx.coroutines.CoroutineScope
import me.amryousef.fpl.data.LoginService
import java.io.Serializable

class LoginStateSaver(
    private val loginService: LoginService,
    private val coroutineScope: CoroutineScope
) : Saver<LoginStateStore, Serializable> {
    override fun restore(value: Serializable): LoginStateStore? {
        return (value as? LoginState)?.let { LoginStateStore(it, loginService, coroutineScope) }
    }

    override fun SaverScope.save(value: LoginStateStore): Serializable {
        return value.state.value
    }
}