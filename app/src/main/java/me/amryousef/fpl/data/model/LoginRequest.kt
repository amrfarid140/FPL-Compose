package me.amryousef.fpl.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "login")
    val login: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "app")
    val app: String,
    @Json(name = "redirect_uri")
    val redirect_uri: String
)