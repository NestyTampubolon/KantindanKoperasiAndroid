package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.room.MyDatabaseBarangSnack
import com.PAM.kantinkoperasi.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*

class BarangDetailActivity : AppCompatActivity() {
    private lateinit var tv_nama : TextView
    private lateinit var  tv_harga  : TextView
    private lateinit var tv_kategori : TextView
    private lateinit var btn_keranjang : RelativeLayout
    private lateinit var btn_tambah : ImageView
    private lateinit var btn_kurang : ImageView
    private lateinit var tv_jumlah : TextView
    private lateinit var tv_stok : TextView
    private lateinit var image : ImageView
    private lateinit var btn_toKeranjang : RelativeLayout

    lateinit var s: SharedPref
    lateinit var myDb: MyDatabaseBarangSnack
    lateinit var barangsnack: BarangSnack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang_detail)
        tv_nama = findViewById(R.id.tv_nama)
        tv_harga = findViewById(R.id.tv_harga)
        tv_kategori = findViewById(R.id.tv_kategori)
        tv_stok = findViewById(R.id.tv_stok)
        image = findViewById(R.id.image)

        btn_keranjang = findViewById(R.id.btn_keranjang)
        btn_tambah = findViewById(R.id.btn_tambah)
        btn_kurang = findViewById(R.id.btn_kurang)
        tv_jumlah = findViewById(R.id.tv_jumlah)

        btn_toKeranjang = findViewById(R.id.btn_toKeranjang)

        myDb = MyDatabaseBarangSnack.getInstance(this)!!
        s = SharedPref(this)

        getInfo()
        mainButton()
    }
    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        barangsnack = Gson().fromJson<BarangSnack>(data, BarangSnack::class.java)

        // set Value
        tv_nama.text = barangsnack.nama
        tv_harga.text = Helper().gantiRupiah(barangsnack.harga)
        tv_kategori.text = barangsnack.kategori
        tv_stok.text = barangsnack.stok.toString()

        val img = Config.productUrl2 + barangsnack.gambar
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .resize(400, 400)
            .into(image)


    }
    private fun mainButton() {
        var jumlah = 1
        btn_keranjang.setOnClickListener {
            jumlah = Integer.valueOf(tv_jumlah.text.toString())
            val data = myDb.daoBarangSnack().getBarangSnack(barangsnack.id_barang_snack)
            if (data == null) {
                barangsnack.jumlah = jumlah
                insert()
            } else {
                data.jumlah += jumlah
                update(data)
            }
            barangsnack.stok -= jumlah
            tv_stok.text = barangsnack.stok.toString()
        }
        btn_tambah.setOnClickListener {
            jumlah = Integer.valueOf(tv_jumlah.text.toString())
            if (jumlah <= barangsnack.stok){
                jumlah++
                tv_jumlah.setText(Integer.toString(jumlah))
            }

        }
        btn_kurang.setOnClickListener {
            jumlah = Integer.valueOf(tv_jumlah.text.toString())
            if (jumlah <= 1) return@setOnClickListener
            else {
                jumlah--
                tv_jumlah.setText(Integer.toString(jumlah))
            }
        }
        btn_toKeranjang.setOnClickListener{
            val intent = Intent(this, KeranjangBarangSnack::class.java)
            startActivity(intent)
        }

    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoBarangSnack().insert(barangsnack) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
            })
    }

    private fun update(data: BarangSnack) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoBarangSnack().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted update")
                Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show()
            })
    }
    private fun checkKeranjang() {
        val dataKeranjang = myDb.daoBarangSnack().getAll()

        if (dataKeranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }
}