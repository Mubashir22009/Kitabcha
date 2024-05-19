package app.kitabcha.source.helper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.minutes

suspend fun OkHttpClient.get(
    url: HttpUrl,
    headers: Headers,
    cache: CacheControl = DEFAULT_CACHE_CONTROL,
): Response {
    val request =
        Request.Builder()
            .url(url)
            .headers(headers)
            .cacheControl(cache)
            .build()

    return newCall(request).await()
}

suspend fun OkHttpClient.get(
    url: String,
    headers: Headers,
    cache: CacheControl = DEFAULT_CACHE_CONTROL,
): Response {
    return get(url.toHttpUrl(), headers, cache)
}

suspend fun OkHttpClient.post(
    url: String,
    headers: Headers,
    body: RequestBody,
    cache: CacheControl = DEFAULT_CACHE_CONTROL,
): Response {
    val request =
        Request.Builder()
            .url(url)
            .headers(headers)
            .post(body)
            .cacheControl(cache)
            .build()

    return newCall(request).await()
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun Call.await(callStack: Array<StackTraceElement>): Response {
    return suspendCancellableCoroutine { continuation ->
        val callback =
            object : okhttp3.Callback {
                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    continuation.resume(response) {
                        response.body.close()
                    }
                }

                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    // Don't bother with resuming the continuation if it is already cancelled.
                    if (continuation.isCancelled) return
                    val exception = IOException(e.message, e).apply { stackTrace = callStack }
                    continuation.resumeWithException(exception)
                }
            }

        enqueue(callback)

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                // Ignore cancel exception
            }
        }
    }
}

suspend fun Call.await(): Response {
    val callStack = Exception().stackTrace.run { copyOfRange(1, size) }
    return await(callStack)
}

private val DEFAULT_CACHE_CONTROL = CacheControl.Builder().maxAge(10.minutes).build()
