package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.adapter.AdapterMakananMinuman
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.model.MakananMinuman
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakananActivity : AppCompatActivity() {
    lateinit var rv_makanan : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)

        rv_makanan = findViewById(R.id.rv_makanan)
        rv_makanan.setHasFixedSize(true)

        getMakanan()

    }

    fun displayMakanan(){
        val layoutManager = LinearLayoutManager(this)

        rv_makanan.adapter = AdapterMakananMinuman(this, listMakananMinuman)
        rv_makanan.layoutManager = layoutManager
        rv_makanan.layoutManager = GridLayoutManager(this, 2)

    }

    private var listMakananMinuman: ArrayList<MakananMinuman> = ArrayList()
    fun getMakanan(){
        ApiConfig.instanceRetrofit.indexMakanan().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    listMakananMinuman= res.makananminumans
                    displayMakanan()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@MakananActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        getMakanan()
        super.onResume()
    }

}