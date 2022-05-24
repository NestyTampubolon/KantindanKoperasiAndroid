package com.PAM.kantinkoperasi.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.room.MyDatabaseBarangSnack
import com.PAM.kantinkoperasi.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableError
import io.reactivex.schedulers.Schedulers

class AdapterKeranjangBarangSnack(var activity: Activity, var data: ArrayList<BarangSnack>, var listener: Listeners) : RecyclerView.Adapter<AdapterKeranjangBarangSnack.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val img_produk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layoutkeranjang)


        val btn_tambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btn_kurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btn_delete = view.findViewById<ImageView>(R.id.btn_delete)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tv_jumlah = view.findViewById<TextView>(R.id.tv_jumlah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val barangsnack = data[position]
        val harga = Integer.valueOf(barangsnack.harga)

        holder.tv_nama.text = barangsnack.nama
        holder.tv_harga.text = Helper().gantiRupiah(harga * barangsnack.jumlah)

        var jumlah = data[position].jumlah
        holder.tv_jumlah.text = jumlah.toString()

        holder.checkBox.isChecked = barangsnack.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            barangsnack.selected = isChecked
            update(barangsnack)
        }

        val image = Config.productUrl2 + data[position].gambar
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .into(holder.img_produk)

        holder.btn_tambah.setOnClickListener {
            jumlah++
            barangsnack.jumlah = jumlah
            update(barangsnack)

            holder.tv_jumlah.text = jumlah.toString()
            holder.tv_harga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btn_kurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            barangsnack.jumlah = jumlah
            update(barangsnack)

            holder.tv_jumlah.text = jumlah.toString()
            holder.tv_harga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btn_delete.setOnClickListener {
            data[position].stok += barangsnack.jumlah
            delete(barangsnack)
            listener.onDelete(position)
        }
    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)
    }

    private fun update(data: BarangSnack) {
        val myDb = MyDatabaseBarangSnack.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoBarangSnack().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: BarangSnack) {
        val myDb = MyDatabaseBarangSnack.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoBarangSnack().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            })
    }

}