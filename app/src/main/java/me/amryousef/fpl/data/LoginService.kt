package me.amryousef.fpl.data

import android.content.SharedPreferences
import me.amryousef.fpl.data.cookies.SerializableCookie
import me.amryousef.fpl.data.model.LoginRequest
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class MyCookieJar(
    private val sharedPreferences: SharedPreferences
) : CookieJar {
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        val editor = sharedPreferences.edit()
        cookies.forEach { cookie ->
            val serializableCookie = SerializableCookie().encode(cookie)
            editor.putString(createCookieKey(cookie), serializableCookie)
        }
        editor.apply()
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return sharedPreferences.all.entries.mapNotNull { entry ->
            (entry.value as? String)?.let { value ->
                SerializableCookie().decode(value)
            }
        }.toMutableList()
    }

    private fun createCookieKey(cookie: Cookie): String {
        return (if (cookie.secure()) "https" else "http") +
            "://" +
            cookie.domain() +
            cookie.path() +
            "|" +
            cookie.name()
    }
}

class LoginService(
    private val api: FplLoginAPI
) {
    private companion object {
        const val LOGIN_REDIRECT_URI = "https://fantasy.premierleague.com/a/login"
        const val LOGIN_APP = "plfpl-web"
    }

    suspend fun login(email: String, password: String) {
        api.login(
            email = email,
            password = password,
            app = LOGIN_APP,
            redirect_uri = LOGIN_REDIRECT_URI
        )
    }
}