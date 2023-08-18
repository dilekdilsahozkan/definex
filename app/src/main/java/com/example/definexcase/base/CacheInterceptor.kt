package com.example.definexcase.base

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.concurrent.TimeUnit

const val CACHE_CONTROL_HEADER = "Cache-Control"
const val CACHE_CONTROL_NO_CACHE = "no-cache"

class CacheInterceptor(private val cache: Cache) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val originalResponse = chain.proceed(request)

            val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
            if (!shouldUseCache) return originalResponse

            if (request.method != "GET") {
                cache.evictAll()
            }

            val cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.HOURS).build()

            return originalResponse.newBuilder()
                .header(CACHE_CONTROL_HEADER, cacheControl.toString())
                .build()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Response.Builder().code(400).request(
                Request.Builder()
                    .method(request.method, null)
                    .addHeader("Accept", "application/json")
                    .url(request.url)
                    .build()
            ).protocol(Protocol.HTTP_1_1).message("Timeout").body(
                "Timeout".toResponseBody()
            ).build()
        }
    }

}