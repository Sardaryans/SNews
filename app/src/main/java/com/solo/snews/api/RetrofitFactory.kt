package com.solo.snews.api

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
        private const val BASE_URL = "https://content.guardianapis.com"

        fun makeRetrofitService(): RetrofitService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .addConverterFactory()
//                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build().create(RetrofitService::class.java)
        }
}