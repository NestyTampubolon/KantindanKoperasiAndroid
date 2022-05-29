package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.adapter.AdapterPulsa
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.model.Pulsa
import com.PAM.kantinkoperasi.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PulsaActivity : AppCompatActivity() {
    lateinit var rv_pulsa : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulsa)

        rv_pulsa = findViewById(R.id.rv_pulsa)
        rv_pulsa.setHasFixedSize(true)

        indexPulsa()

    }

    fun displayPulsa(){
        val layoutManager = LinearLayoutManager(this)

        rv_pulsa.adapter = AdapterPulsa(this, listPulsa)
        rv_pulsa.layoutManager = layoutManager
        rv_pulsa.layoutManager = GridLayoutManager(this, 2)

    }
    private var listPulsa: ArrayList<Pulsa> = ArrayList()
    fun indexPulsa(){
        ApiConfig.instanceRetrofit.indexPulsa().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    listPulsa = res.pulsa
                    displayPulsa()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@PulsaActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}