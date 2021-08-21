package me.amryousef.fpl.data

import com.squareup.moshi.Json
import me.amryousef.fpl.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FplLoginAPI {
    @FormUrlEncoded
    @POST("accounts/login/")
    suspend fun login(
        @Field("login")
        email: String,
        @Field("password")
        password: String,
        @Field("app")
        app: String,
        @Field("redirect_uri")
        redirect_uri: String
    )
}