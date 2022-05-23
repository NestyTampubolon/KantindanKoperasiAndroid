package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.adapter.AdapterKeranjangMakananMinuman
import com.PAM.kantinkoperasi.adapter.AdapterMakananMinuman
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.helper.SharedPref
import com.PAM.kantinkoperasi.model.MakananMinuman
import com.PAM.kantinkoperasi.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KeranjangMakanan : AppCompatActivity() {
    lateinit var btn_deletemakanan : ImageView
    lateinit var rv_makanan : RecyclerView
    lateinit var tv_totalmakanan : TextView
    lateinit var btn_bayarmakanan : TextView
    lateinit var cb_allmakanan : CheckBox
    lateinit var btn_toKeranjang : RelativeLayout

    lateinit var adapter: AdapterKeranjangMakananMinuman
    var listMakananMinuman = ArrayList<MakananMinuman>()

    lateinit var myDb: MyDatabase
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang_makanan)
        myDb = MyDatabase.getInstance(this)!!
        s = SharedPref(this)

        btn_deletemakanan = findViewById(R.id.btn_deletemakanan)
        rv_makanan = findViewById(R.id.rv_makanan)
        tv_totalmakanan = findViewById(R.id.tv_totalmakanan)
        btn_bayarmakanan = findViewById(R.id.btn_bayarmakanan)
        cb_allmakanan = findViewById(R.id.cb_allmakanan)

        displayMakanan()
        mainButton()

    }

    private fun displayMakanan(){
        listMakananMinuman = myDb.daoKeranjangMakananMinuman().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangMakananMinuman(this, listMakananMinuman, object : AdapterKeranjangMakananMinuman.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listMakananMinuman.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rv_makanan.adapter = adapter
        rv_makanan.layoutManager = layoutManager
    }

    var totalHarga = 0
    fun hitungTotal() {
        val listMakananMinuman = myDb.daoKeranjangMakananMinuman().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (makananminuman in listMakananMinuman) {
            if (makananminuman.selected) {
                val harga = Integer.valueOf(makananminuman.harga)
                totalHarga += (harga * makananminuman.jumlah)
            } else {
                isSelectedAll = false
            }
        }

        cb_allmakanan.isChecked = isSelectedAll
        tv_totalmakanan.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton() {
        btn_deletemakanan.setOnClickListener {
            val listDelete = ArrayList<MakananMinuman>()
            for (p in listMakananMinuman) {
                if (p.selected) listDelete.add(p)
            }

            delete(listDelete)
        }

        btn_bayarmakanan.setOnClickListener {

        }

        cb_allmakanan.setOnClickListener {
            for (i in listMakananMinuman.indices) {
                val produk = listMakananMinuman[i]
                produk.selected = cb_allmakanan.isChecked
                listMakananMinuman[i] = produk
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun delete(data: ArrayList<MakananMinuman>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangMakananMinuman().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listMakananMinuman.clear()
                listMakananMinuman.addAll(myDb.daoKeranjangMakananMinuman().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onResume() {
        displayMakanan()
        hitungTotal()
        super.onResume()
    }
}