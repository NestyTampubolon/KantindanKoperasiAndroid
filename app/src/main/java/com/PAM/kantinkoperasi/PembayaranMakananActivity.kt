package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.CheckoutBarang
import com.PAM.kantinkoperasi.model.CheckoutMakanan
import com.PAM.kantinkoperasi.model.ResponModel
import com.PAM.kantinkoperasi.room.MyDatabase
import com.PAM.kantinkoperasi.room.MyDatabaseBarangSnack
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranMakananActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    lateinit var myDbBarangSnack: MyDatabaseBarangSnack
    var totalHarga = 0

    private lateinit var edt_nama : EditText
    private lateinit var edt_phone : EditText
    private lateinit var edt_catatan : EditText
    private lateinit var tv_total : TextView
    private lateinit var edt_total : EditText
    private lateinit var btn_pesan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran_makanan)
        edt_nama = findViewById(R.id.edt_nama)
        edt_phone = findViewById(R.id.edt_phone)
        edt_catatan = findViewById(R.id.edt_catatan)
        tv_total = findViewById(R.id.tv_total)
        edt_total = findViewById(R.id.edt_total)
        btn_pesan = findViewById(R.id.btn_pesan)


        myDb = MyDatabase.getInstance(this)!!
        myDbBarangSnack = MyDatabaseBarangSnack.getInstance(this)!!

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_total.text = Helper().gantiRupiah(totalHarga)

        mainButton()
    }

    private fun mainButton() {
        btn_pesan.setOnClickListener {
            bayar()
        }
    }

    private fun bayar() {
        if (totalHarga == Integer.valueOf(edt_total.text.toString())){
            val user = SharedPref(this).getUser()!!

            if(intent.getStringExtra("jenis") == "barang"){
                val listBarangSnack = myDbBarangSnack.daoBarangSnack().getAll() as ArrayList
                var total_item = 0
                var total_pembayaran = 0
                val barangs = ArrayList<CheckoutBarang.Item>()
                for (p in listBarangSnack) {
                    if (p.selected) {
                        total_item += p.jumlah
                        total_pembayaran += (p.jumlah * Integer.valueOf(p.harga))

                        val barang = CheckoutBarang.Item()
                        barang.id_barang_snack = "" + p.id_barang_snack
                        barang.kuantitas = "" + p.jumlah
                        barang.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
                        barangs.add(barang)
                    }
                }

                val checkout = CheckoutBarang()
                checkout.id_user = "" + user.id_user
                checkout.total_item = "" + total_item
                checkout.total_pembayaran = "" + total_pembayaran
                checkout.nama_penerima = edt_nama.text.toString()
                checkout.nomor_telephone = edt_phone.text.toString()
                checkout.catatan = edt_catatan.text.toString()
                checkout.produks = barangs

                ApiConfig.instanceRetrofit.checkoutbarang(checkout).enqueue(object : Callback<ResponModel> {
                    override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                        error(t.message.toString())
                    }

                    override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                        if (!response.isSuccessful) {
                            error(response.message())
                            return
                        }
                        val respon = response.body()!!
                        if (respon.success == 1) {
                            val jsCheckout = Gson().toJson(checkout, CheckoutBarang::class.java)
                            val intent1 = Intent(this@PembayaranMakananActivity, SuccessMakananActivity::class.java)
                            intent1.putExtra("checkout", jsCheckout)
                            intent1.putExtra("extra", "" + totalHarga)
                            intent1.putExtra("jenis", "barang")
                            startActivity(intent1)
                        } else {
                            error(respon.message)
                            Toast.makeText(this@PembayaranMakananActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }else{
                val listMakananMinuman= myDb.daoKeranjangMakananMinuman().getAll() as ArrayList
                var total_item = 0
                var total_pembayaran = 0
                val produks = ArrayList<CheckoutMakanan.Item>()
                for (p in listMakananMinuman) {
                    if (p.selected) {
                        total_item += p.jumlah
                        total_pembayaran += (p.jumlah * Integer.valueOf(p.harga))

                        val produk = CheckoutMakanan.Item()
                        produk.id_makanan_minuman = "" + p.id_makanan_minuman
                        produk.kuantitas = "" + p.jumlah
                        produk.total_harga = "" + (p.jumlah * Integer.valueOf(p.harga))
                        produks.add(produk)
                    }
                }

                val checkout = CheckoutMakanan()
                checkout.id_user = "" + user.id_user
                checkout.total_item = "" + total_item
                checkout.total_pembayaran = "" + total_pembayaran
                checkout.nama_penerima = edt_nama.text.toString()
                checkout.nomor_telephone = edt_phone.text.toString()
                checkout.catatan = edt_catatan.text.toString()
                checkout.produks = produks

                ApiConfig.instanceRetrofit.checkoutmakanan(checkout).enqueue(object : Callback<ResponModel> {
                    override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                        error(t.message.toString())
                    }

                    override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                        if (!response.isSuccessful) {
                            error(response.message())
                            return
                        }
                        val respon = response.body()!!
                        if (respon.success == 1) {
                            val jsCheckout = Gson().toJson(checkout, CheckoutMakanan::class.java)
                            val intent1 = Intent(this@PembayaranMakananActivity, SuccessMakananActivity::class.java)
                            intent1.putExtra("checkout", jsCheckout)
                            intent1.putExtra("extra", "" + totalHarga)
                            intent1.putExtra("jenis", "produk")
                            startActivity(intent1)
                        } else {
                            error(respon.message)
                            Toast.makeText(this@PembayaranMakananActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }


        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}