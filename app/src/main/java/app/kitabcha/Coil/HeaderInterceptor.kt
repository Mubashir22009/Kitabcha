package app.kitabcha.Coil

import app.kitabcha.source.AvailableSources
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor(val sourceID: Long) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val source = AvailableSources.sources[sourceID]!!
        val request = chain.request().newBuilder()
            .headers(source.headers)
            .build()
        return chain.proceed(request)
    }
}