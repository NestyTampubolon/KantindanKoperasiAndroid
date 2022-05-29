package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.model.CheckoutBarang
import com.PAM.kantinkoperasi.model.CheckoutMakanan
import com.PAM.kantinkoperasi.room.MyDatabase
import com.PAM.kantinkoperasi.room.MyDatabaseBarangSnack
import com.google.gson.Gson

class SuccessMakananActivity : AppCompatActivity() {
    private lateinit var tv_nominal : TextView
    private lateinit var btn_cekStatus : Button
    var nominal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_makanan)

        tv_nominal = findViewById(R.id.tv_nominal)
        btn_cekStatus = findViewById(R.id.btn_cekStatus)

        setValues()
        mainButton()
    }

    fun mainButton() {

        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }
    }

    fun setValues() {
        val jenis = intent.getStringExtra("jenis")

        if(jenis == "barang"){
            val jsCheckout = intent.getStringExtra("checkout")
            nominal = Integer.valueOf(intent.getStringExtra("extra")!!)
            tv_nominal.text = Helper().gantiRupiah(nominal)
            val checkoutBarang = Gson().fromJson(jsCheckout, CheckoutBarang::class.java)
            // hapus keranjang
            val myDb = MyDatabaseBarangSnack.getInstance(this)!!
            for (BarangSnack in checkoutBarang.produks){
                myDb.daoBarangSnack().deleteById(BarangSnack.id_barang_snack)
            }
        }else if(jenis == "produk"){
            val jsCheckout = intent.getStringExtra("checkout")
            nominal = Integer.valueOf(intent.getStringExtra("extra")!!)
            tv_nominal.text = Helper().gantiRupiah(nominal)
            val checkoutMakanan = Gson().fromJson(jsCheckout, CheckoutMakanan::class.java)
            // hapus keranjang
            val myDb = MyDatabase.getInstance(this)!!
            for (MakananMinuman in checkoutMakanan.produks){
                myDb.daoKeranjangMakananMinuman().deleteById(MakananMinuman.id_makanan_minuman)
            }
        }else if(jenis == "pulsa"){
            nominal = Integer.valueOf(intent.getStringExtra("total_harga")!!)
            tv_nominal.text = Helper().gantiRupiah(nominal)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}