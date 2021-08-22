package me.amryousef.fpl.ui.screens.main

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import me.amryousef.fpl.data.LoginService
import java.io.Serializable

class AppStateSaver(
    private val loginService: LoginService
) : Saver<AppStateStore, Serializable> {
    override fun restore(value: Serializable): AppStateStore {
        return AppStateStore(
            initialState = value as? AppState,
            loginService = loginService
        )
    }

    override fun SaverScope.save(value: AppStateStore): Serializable {
        return value.state.value
    }
}