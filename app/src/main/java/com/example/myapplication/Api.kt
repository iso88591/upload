package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {
    //https://www.google.com/search?q=%E5%9C%B0%E6%96%B9
    @GET("/s")
    fun getString(@Query("wd")q:String): Call<String>



}
