package com.PAM.kantinkoperasi.app

import com.PAM.kantinkoperasi.model.CheckOutPulsa
import com.PAM.kantinkoperasi.model.CheckoutBarang
import com.PAM.kantinkoperasi.model.CheckoutMakanan
import okhttp3.ResponseBody
import retrofit2.Call
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("noKTP") noKTP: String,
        @Field("noHandphone") noHandphone: String,
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

    @POST("checkoutbarang")
    fun checkoutbarang(
        @Body data: CheckoutBarang
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("checkoutpulsa")
    fun checkoutpulsa(
        @Field("id_pulsa") id_pulsa: String,
        @Field("id_user") id_user: String,
        @Field("nomor_telephone") nomor_telephone: String,
    ): Call<ResponseBody>


    @FormUrlEncoded
    @POST("bookingruangan")
    fun bookingruangan(
        @Field("id_user") id_user: String,
        @Field("tanggal_pemesanan") tanggal_pemesanan: String,
        @Field("nama_ruangan") nama_ruangan: String,
        @Field("jam_mulai") jam_mulai: String,
        @Field("jam_selesai") jam_selesai: String,
        @Field("deskripsi") deskripsi: String,
    ): Call<ResponseBody>

    @GET("checkoutmakanan/user/{id}")
    fun getRiwayatMakanan(
        @Path("id") id: Int
    ): Call<ResponModel>

    @DELETE("checkoutmakanan/delete/{id}")
    fun deletePemesananMakanan(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @GET("checkoutbarang/user/{id}")
    fun getRiwayatBarang(
        @Path("id") id: Int
    ): Call<ResponModel>

    @DELETE("checkoutbarang/delete/{id}")
    fun deletePemesananBarang(
        @Path("id") id: Int
    ): Call<ResponseBody>


    @GET("checkoutpulsa/user/{id}")
    fun getRiwayatPulsa(
        @Path("id") id: Int
    ): Call<ResponModel>

    @DELETE("checkoutpulsa/delete/{id}")
    fun deletePemesananPulsa(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @GET("bookingruangan/user/{id}")
    fun getRiwayatBooking(
        @Path("id") id: Int
    ): Call<ResponModel>

    @DELETE("bookingruangan/delete/{id}")
    fun deleteBookingRuangan(
        @Path("id") id: Int
    ): Call<ResponseBody>
}