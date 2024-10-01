package com.example.test3

import com.example.test3.constants.Urls
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(Urls.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}