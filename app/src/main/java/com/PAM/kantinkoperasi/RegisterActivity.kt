package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.PAM.kantinkoperasi.app.ApiConfig

class RegisterActivity : AppCompatActivity() {
    lateinit var  tv_email : EditText
    lateinit var tv_nim : EditText
    lateinit var tv_nama : EditText
    lateinit var tv_password : EditText
    lateinit var btn_daftar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_email = findViewById(R.id.tv_email)
        tv_nim  = findViewById(R.id.tv_nim)
        tv_nama  = findViewById(R.id.tv_nama)
        tv_password  = findViewById(R.id.tv_password)
        btn_daftar = findViewById(R.id.btn_daftar)

        btn_daftar.setOnClickListener {
            daftar()
        }
    }

    fun daftar(){
        if (tv_email.text.isEmpty()){
            tv_email.error = "Kolom nama tidak boleh kosong"
            tv_email.requestFocus()
            return
        }else if (tv_nim.text.isEmpty()){
            tv_nim.error = "Kolom nama tidak boleh kosong"
            tv_nim.requestFocus()
            return
        }else if (tv_nama.text.isEmpty()){
            tv_nama.error = "Kolom nama tidak boleh kosong"
            tv_nama.requestFocus()
            return
        }else if (tv_password.text.isEmpty()){
            tv_password.error = "Kolom nama tidak boleh kosong"
            tv_password.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.register(tv_nim.text.toString(),tv_nama.text.toString(),tv_email.text.toString(),tv_password.text.toString()).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RegisterActivity, "Success: Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}