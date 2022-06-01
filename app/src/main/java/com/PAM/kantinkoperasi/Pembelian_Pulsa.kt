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
import com.PAM.kantinkoperasi.model.Pulsa
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pembelian_Pulsa : AppCompatActivity() {
    var totalHarga = 0

    private lateinit var edt_phone : EditText
    private lateinit var tv_nominal : TextView
    private lateinit var tv_total : TextView
    private lateinit var edt_total : EditText
    private lateinit var btn_bayar : Button

    lateinit var pulsa: Pulsa
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian_pulsa)

        s = SharedPref(this)
        edt_phone = findViewById(R.id.edt_phone)
        tv_total = findViewById(R.id.tv_total)
        tv_nominal = findViewById(R.id.tv_nominal)
        edt_total = findViewById(R.id.edt_total)
        btn_bayar = findViewById(R.id.btn_bayar)


        val data = intent.getStringExtra("extra")
        pulsa = Gson().fromJson<Pulsa>(data, Pulsa::class.java)
        tv_nominal.text = pulsa.nama
        tv_total.text = Helper().gantiRupiah(pulsa.harga)

        mainButton()
    }

    private fun mainButton() {
        btn_bayar.setOnClickListener {
            bayar()
        }
    }

    private fun bayar() {
        if (s.getStatusLogin()) {
            if (edt_phone.text.isEmpty()) {
                edt_phone.error = "Kolom nomor handphone tidak boleh kosong"
                edt_phone.requestFocus()
                return
            } else if (edt_total.text.isEmpty()) {
                edt_total.error = "Kolom total pembayaran tidak boleh kosong"
                edt_total.requestFocus()
                return
            }

            if (pulsa.harga == Integer.valueOf(edt_total.text.toString())) {
                val user = SharedPref(this).getUser()!!
                val total_harga = pulsa.harga

                ApiConfig.instanceRetrofit.checkoutpulsa(
                    pulsa.id_pulsa.toString(),
                    user.id_user.toString(),
                    edt_phone.text.toString()
                ).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Toast.makeText(
                            this@Pembelian_Pulsa,
                            "Pemesanan Pula Anda Sedang Diproses",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent1 =
                            Intent(this@Pembelian_Pulsa, SuccessMakananActivity::class.java)
                        intent1.putExtra("total_harga", "" + total_harga)
                        intent1.putExtra("jenis", "pulsa")
                        startActivity(intent1)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }

                })
            } else {
                Toast.makeText(this, "Total Pembayaran tidak sesuai", Toast.LENGTH_SHORT).show()
            }
        }else {
            this.startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}