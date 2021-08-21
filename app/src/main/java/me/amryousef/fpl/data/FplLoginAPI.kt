package me.amryousef.fpl.data

import retrofit2.http.POST

interface FplLoginAPI {
    @POST("accounts/login/")
    suspend fun login(
        email: String,
        password: String
    )
}