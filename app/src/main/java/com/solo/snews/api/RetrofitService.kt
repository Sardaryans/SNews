package com.solo.snews.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/search")
    fun getNews(@Query("format")format: String,
                @Query("use-date")useDate: String,
                @Query("page-size")pageSize: String,
                @Query("page")currentPage: String,
                @Query("show-fields")fields: String,
                @Query("api-key")key: String): Call<ResponseBody>
}