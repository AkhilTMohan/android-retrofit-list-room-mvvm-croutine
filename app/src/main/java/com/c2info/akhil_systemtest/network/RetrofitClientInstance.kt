package com.c2info.akhil_systemtest.network

import com.c2info.akhil_systemtest.BuildConfig
import com.c2info.akhil_systemtest.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientInstance {
    var BASE_URL = ""
    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            val logging = HttpLoggingInterceptor()
            logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
            if (DEBUG) {
                httpClient.addInterceptor(logging)
            }
            /*httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request =
                        chain.request().newBuilder()
                            .addHeader("Host", "braveheartcreations.com")
                            .build()
                    return chain.proceed(request)
                }
            })*/

            if (retrofit == null) {
                BASE_URL = BuildConfig.BASE_URL
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit
        }
}