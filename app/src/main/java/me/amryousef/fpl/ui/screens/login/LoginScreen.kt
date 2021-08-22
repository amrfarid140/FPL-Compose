package me.amryousef.fpl.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.amryousef.fpl.LocalServiceProvider
import me.amryousef.fpl.data.LoginService
import me.amryousef.fpl.ui.theme.FPLTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val stateStore = loginContext()
    val state by stateStore.state
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            placeholder = { Text("Email") },
            label = { Text("Email") },
            enabled = !state.isSubmitting,
            value = state.email,
            onValueChange = {
                stateStore.onEmailChanged(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        OutlinedTextField(
            placeholder = { Text("Password") },
            label = { Text("Password") },
            enabled = !state.isSubmitting,
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
        Row {
            Button(
                onClick = { stateStore.doLogin() },
                enabled = !state.isSubmitting
            ) {
                Text(text = "Login")
            }
        }
    }
    if (state.shouldDisplayErrorAlert) {
        AlertDialog(
            onDismissRequest = { stateStore.onErrorAlertDismissed() },
            title = { Text("Login Error") },
            text = {
                Text(
                    text = "There was an error logging in. Check your email and password then try again."
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { stateStore.onErrorAlertDismissed() }) {
                        Text(text = "OK")
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