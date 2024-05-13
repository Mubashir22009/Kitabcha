package app.kitabcha.source.helper

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class NetworkHelper(
    context: Context
) {
    private val cookieJar = AndroidCookieJar()

    val client: OkHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .connectTimeout(30.seconds)
        .readTimeout(30.seconds)
        .callTimeout(2.minutes)
        .cache(
            Cache(
                directory = File(context.cacheDir, "network_cache"),
                maxSize = 5L * 1024 * 1024
            )
        )
        .build()
}