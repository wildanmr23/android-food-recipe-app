package com.example.foodrecipeapp.entities.retrofitclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {

    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        private val client:OkHttpClient
            get() {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                return OkHttpClient.Builder().addInterceptor(interceptor).build()
            }
        val retrofitInstance: Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                }
                return retrofit!!
            }

    }
}