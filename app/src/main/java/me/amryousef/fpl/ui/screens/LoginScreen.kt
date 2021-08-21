package me.amryousef.fpl.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amryousef.fpl.ui.theme.FPLTheme
import java.io.Serializable

data class LoginStateData(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean
) : Serializable

class LoginStateStore(
    initialValue: LoginStateData? = null
) {
    val state = mutableStateOf(
        initialValue ?: LoginStateData("", "", false)
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
}

class LoginStateSaver : Saver<LoginStateStore, Serializable> {
    override fun restore(value: Serializable): LoginStateStore? {
        return (value as? LoginStateData)?.let { LoginStateStore(it) }
    }

    override fun SaverScope.save(value: LoginStateStore): Serializable {
        return value.state.value
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val stateStore = rememberSaveable(saver = LoginStateSaver()) { LoginStateStore() }
    val state by stateStore.state
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                stateStore.onEmailChanged(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                stateStore.onPasswordChanged(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconToggleButton(
                    checked = state.isPasswordVisible,
                    onCheckedChange = { stateStore.onPasswordVisibilityToggled() }) {
                    if (state.isPasswordVisible) {
                        Icon(imageVector = Icons.Rounded.Visibility, contentDescription = "")
                    } else {
                        Icon(imageVector = Icons.Rounded.VisibilityOff, contentDescription = "")
                    }
                }
            }
        )
    }
}

@Preview(device = Devices.PIXEL_4, showBackground = true)
@Composable
fun LoginPreview() {
    FPLTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}