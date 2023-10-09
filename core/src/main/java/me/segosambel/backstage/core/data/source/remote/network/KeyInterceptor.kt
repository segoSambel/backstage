package me.segosambel.backstage.core.data.source.remote.network

import me.segosambel.backstage.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.API_KEY
        val req = chain.request().newBuilder().addHeader("Authorization", "Bearer %s".format(token))
        return chain.proceed(req.build())
    }
}