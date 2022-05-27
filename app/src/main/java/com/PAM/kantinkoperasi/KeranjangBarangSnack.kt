package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.adapter.AdapterKeranjangBarangSnack
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.room.MyDatabaseBarangSnack
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KeranjangBarangSnack : AppCompatActivity() {
    lateinit var btn_deletemakanan : ImageView
    lateinit var rv_makanan : RecyclerView
    lateinit var tv_totalmakanan : TextView
    lateinit var btn_bayarmakanan : TextView
    lateinit var cb_allmakanan : CheckBox
    lateinit var btn_toKeranjang : RelativeLayout

    lateinit var adapter: AdapterKeranjangBarangSnack
    var listBarangSnack = ArrayList<BarangSnack>()

    lateinit var myDb: MyDatabaseBarangSnack
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang_barang_snack)
        myDb = MyDatabaseBarangSnack.getInstance(this)!!
        s = SharedPref(this)

        btn_deletemakanan = findViewById(R.id.btn_deletemakanan)
        rv_makanan = findViewById(R.id.rv_makanan)
        tv_totalmakanan = findViewById(R.id.tv_totalmakanan)
        btn_bayarmakanan = findViewById(R.id.btn_bayarmakanan)
        cb_allmakanan = findViewById(R.id.cb_allmakanan)

        displayBarangSnack()
        mainButton()

    }

    private fun displayBarangSnack(){
        listBarangSnack = myDb.daoBarangSnack().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangBarangSnack(this, listBarangSnack, object : AdapterKeranjangBarangSnack.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listBarangSnack.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rv_makanan.adapter = adapter
        rv_makanan.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listBarangSnack = myDb.daoBarangSnack().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (barangsnack in listBarangSnack) {
            if (barangsnack.selected) {
                val harga = Integer.valueOf(barangsnack.harga)
                totalHarga += (harga * barangsnack.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cb_allmakanan.isChecked = isSelectedAll
        tv_totalmakanan.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btn_deletemakanan.setOnClickListener {
            val listDelete = ArrayList<BarangSnack>()
            for (p in listBarangSnack) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }

        btn_bayarmakanan.setOnClickListener {
            if (s.getStatusLogin()) {
                var isThereProduk = false
                for (p in listBarangSnack) {
                    if (p.selected) isThereProduk = true
                }

                if (isThereProduk) {
                    val intent = Intent(this, PembayaranMakananActivity::class.java)
                    intent.putExtra("extra", "" + totalHarga)
                    intent.putExtra("jenis", "barang")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Tidak ada produk yg terpilih", Toast.LENGTH_SHORT).show()
                }
            } else {
                this.startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        cb_allmakanan.setOnClickListener {
            for (i in listBarangSnack.indices) {
                val produk = listBarangSnack[i]
                produk.selected = cb_allmakanan.isChecked
                listBarangSnack[i] = produk
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun delete(data: ArrayList<BarangSnack>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoBarangSnack().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listBarangSnack.clear()
                listBarangSnack.addAll(myDb.daoBarangSnack().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onResume() {
        displayBarangSnack()
        hitungTotal()
        super.onResume()
    }
}