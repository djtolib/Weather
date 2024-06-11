package com.tolib.weather.data.repository

import com.tolib.weather.APP_ID
import com.tolib.weather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter(APP_ID, BuildConfig.API_KEY)
            .build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}