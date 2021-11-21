package com.example.nvesttask.retrofit

import com.example.nvesttask.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getProductList() : Call<List<Product>>

}