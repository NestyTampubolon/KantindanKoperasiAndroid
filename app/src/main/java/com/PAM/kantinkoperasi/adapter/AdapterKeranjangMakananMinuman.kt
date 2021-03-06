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
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.MakananMinuman
import com.PAM.kantinkoperasi.room.MyDatabase
import com.PAM.kantinkoperasi.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterKeranjangMakananMinuman(var activity: Activity, var data: ArrayList<MakananMinuman>, var listener: Listeners) : RecyclerView.Adapter<AdapterKeranjangMakananMinuman.Holder>() {
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

        val makananminuman = data[position]
        val harga = Integer.valueOf(makananminuman.harga)

        holder.tv_nama.text = makananminuman.nama
        holder.tv_harga.text = Helper().gantiRupiah(harga * makananminuman.jumlah)

        var jumlah = data[position].jumlah
        holder.tv_jumlah.text = jumlah.toString()

        holder.checkBox.isChecked = makananminuman.selected
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            makananminuman.selected = isChecked
            update(makananminuman)
        }

        val image = Config.productUrl + data[position].gambar
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .into(holder.img_produk)

        holder.btn_tambah.setOnClickListener {
            jumlah++
            makananminuman.jumlah = jumlah
            update(makananminuman)

            holder.tv_jumlah.text = jumlah.toString()
            holder.tv_harga.text = Helper().gantiRupiah(harga * jumlah)
            makananminuman.stok--

            ApiConfig.instanceRetrofit.updatestok( makananminuman.id_makanan_minuman.toString(),makananminuman.stok.toString()).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }

            })
        }

        holder.btn_kurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            makananminuman.jumlah = jumlah
            update(makananminuman)

            holder.tv_jumlah.text = jumlah.toString()
            holder.tv_harga.text = Helper().gantiRupiah(harga * jumlah)

            makananminuman.stok++

            ApiConfig.instanceRetrofit.updatestok( makananminuman.id_makanan_minuman.toString(),makananminuman.stok.toString()).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }

            })
        }

        holder.btn_delete.setOnClickListener {
            data[position].stok += makananminuman.jumlah
            delete(makananminuman)
            listener.onDelete(position)
        }
    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)
    }

    private fun update(data: MakananMinuman) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMakananMinuman().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: MakananMinuman) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangMakananMinuman().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            })
    }
}