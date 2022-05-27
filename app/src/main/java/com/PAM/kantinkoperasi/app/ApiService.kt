package com.PAM.kantinkoperasi.app

import com.PAM.kantinkoperasi.model.CheckOutPulsa
import com.PAM.kantinkoperasi.model.CheckoutMakanan
import okhttp3.ResponseBody
import retrofit2.Call
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.http.*

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

    @GET("pulsa")
    fun indexPulsa(): Call<ResponModel>


    @GET("makanan")
    fun indexMakanan(): Call<ResponModel>

    @FormUrlEncoded
    @PUT("makanan/updatestok/{id_makanan_minuman}")
    fun updatestok(
        @Path("id_makanan_minuman") id_makanan_minuman: String,
        @Field("stok") stok: String,
    ): Call<ResponseBody>

    @GET("barang")
    fun indexBarang(): Call<ResponModel>


    @FormUrlEncoded
    @POST("tambahkeranjang")
    fun tambahkeranjang(
        @Field("id_user") id_user: String,
        @Field("id_makanan_minuman") id_makanan_minuman: String,
        @Field("kuantitas") kuantitas: String,
        @Field("total") total: String,
    ): Call<ResponseBody>

    @POST("checkoutmakanan")
    fun checkoutmakanan(
        @Body data: CheckoutMakanan
    ): Call<ResponModel>

    @POST("checkoutpulsa")
    fun checkoutpulsa(
        @Body data: CheckOutPulsa
    ): Call<ResponModel>



}