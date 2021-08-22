package me.amryousef.fpl.data

class LoginService(
    private val api: FplLoginAPI,
    private val fplCookieJar: FplCookieJar
) {
    private companion object {
        const val LOGIN_REDIRECT_URI = "https://fantasy.premierleague.com/a/login"
        const val LOGIN_APP = "plfpl-web"
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            api.login(
                email = email,
                password = password,
                app = LOGIN_APP,
                redirect_uri = LOGIN_REDIRECT_URI
            )
            if (fplCookieJar.cookieList.isEmpty()) {
                Result.failure(IllegalStateException("No cookies set"))
            } else {
                Result.success(Unit)
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}