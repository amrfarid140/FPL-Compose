package me.amryousef.fpl.data

import android.content.SharedPreferences
import me.amryousef.fpl.data.cookies.SerializableCookie
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*

class FplCookieJar(
    private val sharedPreferences: SharedPreferences
) : CookieJar {

    val cookieList = Collections.synchronizedList(
        mutableListOf<Cookie>()
    )

    init {
        val cookies = sharedPreferences.all.entries.mapNotNull { entry ->
            (entry.value as? String)?.let { value ->
                SerializableCookie().decode(value)
            }
        }.toMutableList()
        cookieList.addAll(cookies)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cookieList.addAll(cookies)
        val editor = sharedPreferences.edit()
        cookies.forEach { cookie ->
            val serializableCookie = SerializableCookie().encode(cookie)
            editor.putString(createCookieKey(cookie), serializableCookie)
        }
        editor.apply()
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return cookieList
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