package me.amryousef.fpl

import android.app.Application
import android.content.Context
import me.amryousef.fpl.data.FplLoginAPI
import me.amryousef.fpl.data.LoginService
import me.amryousef.fpl.data.MyCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceLoader(
    newApplication: Application? = null
) {

    private val application = newApplication!!

    private val cookiesSharedPref by lazy {
        application.getSharedPreferences(
            "cookie_jar",
            Context.MODE_PRIVATE
        )
    }

    private val cookieJar by lazy {
        MyCookieJar(
            cookiesSharedPref
        )
    }

    private val httpClient by lazy {
        OkHttpClient
            .Builder()
            .cookieJar(cookieJar)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    private val loginRetrofit by lazy {
        Retrofit
            .Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://users.premierleague.com/")
            .build()
    }

    val loginService by lazy {
        LoginService(
            loginRetrofit.create(FplLoginAPI::class.java)
        )
    }
}