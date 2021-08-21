package me.amryousef.fpl.ui.screens.login

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import me.amryousef.fpl.data.LoginService
import java.io.Serializable

class LoginStateSaver(
    private val loginService: LoginService
) : Saver<LoginStateStore, Serializable> {
    override fun restore(value: Serializable): LoginStateStore? {
        return (value as? LoginState)?.let { LoginStateStore(it, loginService) }
    }

    override fun SaverScope.save(value: LoginStateStore): Serializable {
        return value.state.value
    }
}