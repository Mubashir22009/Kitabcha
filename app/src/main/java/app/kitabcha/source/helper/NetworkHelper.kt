package app.kitabcha.source.helper

import okhttp3.OkHttpClient
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object NetworkHelper {
    private val cookieJar = AndroidCookieJar()

    val client: OkHttpClient =
        OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .connectTimeout(30.seconds)
            .readTimeout(30.seconds)
            .callTimeout(2.minutes)
            .build()
}
