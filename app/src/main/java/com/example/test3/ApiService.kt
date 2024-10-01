package com.example.test3

import com.example.test3.constants.Urls
import com.example.test3.models.AllProducts
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Urls.ALL_PRODUCTS)
    suspend fun getAllProducts() : Response<AllProducts>
}