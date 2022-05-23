package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.adapter.AdapterBarangSnack
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangActivity : AppCompatActivity() {
    lateinit var rv_barang : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang)

        rv_barang = findViewById(R.id.rv_barang)
        rv_barang.setHasFixedSize(true)

        getBarang()
    }

    fun displayBarang(){
        val layoutManager = LinearLayoutManager(this)

        rv_barang.adapter = AdapterBarangSnack(this, listBarangSnack)
        rv_barang.layoutManager = layoutManager
        rv_barang.layoutManager = GridLayoutManager(this, 2)

    }
    private var listBarangSnack: ArrayList<BarangSnack> = ArrayList()
    fun getBarang(){
        ApiConfig.instanceRetrofit.indexBarang().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    listBarangSnack= res.barangsnacks
                    displayBarang()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@BarangActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}