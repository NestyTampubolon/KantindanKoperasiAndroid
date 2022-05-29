package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatBookingRuangan
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananBarangSnack
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananMakananMinuman
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananPulsa
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
    }

    fun getRiwayat() {
        val id = SharedPref(this).getUser()!!.id_user
        ApiConfig.instanceRetrofit.getRiwayatMakanan(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    displayRiwayat(res.pemesananmakananminumans)
                }
            }
        })

        ApiConfig.instanceRetrofit.getRiwayatBarang(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    displayRiwayatBarang(res.pemesananbarangsnacks)
                }
            }
        })

        ApiConfig.instanceRetrofit.getRiwayatPulsa(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    displayRiwayatPulsa(res.pemesananpulsas)
                }
            }
        })

        ApiConfig.instanceRetrofit.getRiwayatBooking(id).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    displayRiwayatBooking(res.bookings)
                }
            }
        })
    }

    fun displayRiwayat(pemesananmakananminuman: ArrayList<PemesananMakananMinuman>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayatmakanan.adapter = AdapterRiwayatPemesananMakananMinuman(pemesananmakananminuman, object : AdapterRiwayatPemesananMakananMinuman.Listeners {
            override fun onClicked(data: PemesananMakananMinuman) {
                val json = Gson().toJson(data, PemesananMakananMinuman::class.java)
                val intent = Intent(this@RiwayatActivity, RiwayatMakananMinumanDetail::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }
        })
        rv_riwayatmakanan.layoutManager = layoutManager

    }

    fun displayRiwayatBooking(booking: ArrayList<BookingRuangan>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        rv_riwayatbooking.adapter = AdapterRiwayatBookingRuangan(booking, object : AdapterRiwayatBookingRuangan.Listeners {
            override fun onClicked(data: BookingRuangan) {
                val json = Gson().toJson(data, BookingRuangan::class.java)
                val intent = Intent(this@RiwayatActivity, RiwayatBookingRuanganDetailActivity::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }


        })
        rv_riwayatbooking.layoutManager = layoutManager
    }

    fun displayRiwayatBarang(pemesananbarangsnack: ArrayList<PemesananBarangSnack>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayatbarang.adapter = AdapterRiwayatPemesananBarangSnack(pemesananbarangsnack, object : AdapterRiwayatPemesananBarangSnack.Listeners {
            override fun onClicked(data: PemesananBarangSnack) {
                val json = Gson().toJson(data, PemesananBarangSnack::class.java)
                val intent = Intent(this@RiwayatActivity, RiwayatBarangSnackDetail::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }
        })
        rv_riwayatbarang.layoutManager = layoutManager

    }

    fun displayRiwayatPulsa(pemesananpulsa: ArrayList<PemesananPulsa>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayatpulsa.adapter = AdapterRiwayatPemesananPulsa(pemesananpulsa, object : AdapterRiwayatPemesananPulsa.Listeners {
            override fun onClicked(data: PemesananPulsa) {
                val json = Gson().toJson(data, PemesananPulsa::class.java)
                val intent = Intent(this@RiwayatActivity, RiwayatPemesananPulsaDetail::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }
        })
        rv_riwayatpulsa.layoutManager = layoutManager

    }
    override fun onResume() {
        getRiwayat()
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}