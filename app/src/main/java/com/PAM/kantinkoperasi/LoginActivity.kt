package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.PAM.kantinkoperasi.app.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.ResponModel

class LoginActivity : AppCompatActivity() {
    lateinit var tv_register : TextView
    lateinit var edt_email : EditText
    lateinit var edt_password : EditText
    lateinit var btn_login : Button

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register = findViewById(R.id.tv_register)
        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)
        btn_login = findViewById(R.id.btn_login)

        s = SharedPref(this)

        tv_register.setOnClickListener {
            register()
        }

        btn_login.setOnClickListener {
            login()
        }
    }

    fun register(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun login(){
        if (edt_email.text.isEmpty()){
            edt_email.error = "Kolom nama tidak boleh kosong"
            edt_email.requestFocus()
            return
        }else if (edt_password.text.isEmpty()){
            edt_password.error = "Kolom nama tidak boleh kosong"
            edt_password.requestFocus()
            return
        }



        ApiConfig.instanceRetrofit.login(edt_email.text.toString(),edt_password.text.toString()).enqueue(object :
            Callback<ResponModel> {


            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                if (respon.success == 1) {
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
                    Toast.makeText(
                        this@LoginActivity,
                        "Selamat datang " + respon.user.name ,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}