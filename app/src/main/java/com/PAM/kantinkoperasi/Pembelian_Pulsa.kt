package com.PAM.kantinkoperasi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.CheckOutPulsa
import com.PAM.kantinkoperasi.model.ResponModel
import com.PAM.kantinkoperasi.room.MyDatabasePulsa
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pembelian_Pulsa : AppCompatActivity() {
    lateinit var myDb: MyDatabasePulsa
    var totalHarga = 0

    private lateinit var edt_phone : EditText
    private lateinit var tv_nominal : TextView
    private lateinit var tv_total : TextView
    private lateinit var edt_total : EditText
    private lateinit var btn_bayar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian_pulsa)
   
        edt_phone = findViewById(R.id.edt_phone)
        tv_total = findViewById(R.id.tv_total)
        tv_nominal = findViewById(R.id.tv_nominal)
        edt_total = findViewById(R.id.edt_total)
        btn_bayar = findViewById(R.id.btn_bayar)


        myDb = MyDatabasePulsa.getInstance(this)!!

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_total.text = Helper().gantiRupiah(totalHarga)

        mainButton()
    }

    private fun mainButton() {
        btn_bayar.setOnClickListener {
            bayar()
        }
    }

    private fun bayar() {
        if (totalHarga == Integer.valueOf(edt_total.text.toString())){
            val user = SharedPref(this).getUser()!!

            val listPulsa= myDb.daoPulsa().getAll() as ArrayList
            var total_pembayaran = 0
            val Pulsa = ArrayList<CheckOutPulsa.Item>()
            for (p in listPulsa) {
                if (p.selected) {
                    total_pembayaran = Integer.valueOf(p.harga)

                    val pulsa = CheckOutPulsa.Item()
                    pulsa.id_pulsa = "" + p.id_pulsa
                    pulsa.nominal = "" + p.harga
                    pulsa.total_harga = "" + Integer.valueOf(p.harga)
                    Pulsa.add(pulsa)
                }
            }

            val checkout = CheckOutPulsa()
            checkout.id_user = "" + user.id_user
            checkout.total_pembayaran = "" + total_pembayaran
            checkout.nomor_telephone = edt_phone.text.toString()

            checkout.pulsa = Pulsa

//            ApiConfig.instanceRetrofit.checkoutpulsa(checkout).enqueue(object : Callback<ResponModel> {
//                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                    error(t.message.toString())
//                }
//
//                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                    if (!response.isSuccessful) {
//                        error(response.message())
//                        return
//                    }
//                    val respon = response.body()!!
//                    if (respon.success == 1) {
//                        val jsCheckout = Gson().toJson(checkout, CheckOutPulsa::class.java)
//                        val intent1 = Intent(this@Pembelian_Pulsa, SuccessMakananActivity::class.java)
//                        intent1.putExtra("checkout", jsCheckout)
//                        startActivity(intent1)
//                    } else {
//                        error(respon.message)
//                        Toast.makeText(this@Pembelian_Pulsa, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}