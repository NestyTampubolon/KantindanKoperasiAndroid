package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.MakananMinuman
import com.PAM.kantinkoperasi.room.MyDatabase
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*


class MakananDetailActivity : AppCompatActivity() {
    private lateinit var tv_nama : TextView
    private lateinit var  tv_harga  :TextView
    private lateinit var tv_kategori : TextView
    private lateinit var btn_keranjang : RelativeLayout
    private lateinit var btn_tambah : ImageView
    private lateinit var btn_kurang : ImageView
    private lateinit var tv_jumlah : TextView
    private lateinit var btn_toKeranjang : RelativeLayout

    lateinit var s: SharedPref
    lateinit var myDb: MyDatabase
    lateinit var makananminuman: MakananMinuman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan_detail)
        tv_nama = findViewById(R.id.tv_nama)
        tv_harga = findViewById(R.id.tv_harga)
        tv_kategori = findViewById(R.id.tv_kategori)

        btn_keranjang = findViewById(R.id.btn_keranjang)
        btn_tambah = findViewById(R.id.btn_tambah)
        btn_kurang = findViewById(R.id.btn_kurang)
        tv_jumlah = findViewById(R.id.tv_jumlah)

        btn_toKeranjang = findViewById(R.id.btn_toKeranjang)

        myDb = MyDatabase.getInstance(this)!!
        s = SharedPref(this)

        getInfo()
        mainButton()
    }


    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        makananminuman = Gson().fromJson<MakananMinuman>(data, MakananMinuman::class.java)

        // set Value
        tv_nama.text = makananminuman.nama
        tv_harga.text = Helper().gantiRupiah(makananminuman.harga)
        tv_kategori.text = makananminuman.kategori

    }
    private fun mainButton() {
        var jumlah = Integer.parseInt(tv_jumlah.text.toString())
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjangMakananMinuman().getMakananMinuman(makananminuman.id_makanan_minuman)
            if (data == null) {
                insert()
            } else {
                data.jumlah += 1
                update(data)
            }
        }
        btn_tambah.setOnClickListener {
            Toast.makeText(
                this,
                jumlah ,
                Toast.LENGTH_SHORT
            ).show()

        }
        btn_kurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            else {
                jumlah--
                tv_jumlah.setText(jumlah)
            }
        }
        btn_toKeranjang.setOnClickListener{
            val intent = Intent(this, KeranjangMakanan::class.java)
            startActivity(intent)
        }

    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakananMinuman().insert(makananminuman) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
            })
    }

    private fun update(data: MakananMinuman) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakananMinuman().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted update")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }
    private fun checkKeranjang() {
        val dataKeranjang = myDb.daoKeranjangMakananMinuman().getAll()

        if (dataKeranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }


}