package com.PAM.kantinkoperasi.app

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.http.GET

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nim") nim: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponModel>


    @GET("makanan")
    fun indexMakanan(): Call<ResponModel>

    @GET("barang")
    fun indexBarang(): Call<ResponModel>

}