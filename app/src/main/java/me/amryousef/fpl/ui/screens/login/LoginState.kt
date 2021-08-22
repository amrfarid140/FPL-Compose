package me.amryousef.fpl.ui.screens.login

import java.io.Serializable

data class LoginState(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean,
    val isSubmitting: Boolean,
    val shouldDisplayErrorAlert: Boolean
) : Serializable