package com.PAM.kantinkoperasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.BarangActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BarangSnack
import com.PAM.kantinkoperasi.util.Config
import com.squareup.picasso.Picasso

class AdapterBarangSnack(var activity: BarangActivity, var data:ArrayList<BarangSnack>): RecyclerView.Adapter<AdapterBarangSnack.Holder>(){

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_kategori = view.findViewById<TextView>(R.id.tv_kategori)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val img_makanan = view.findViewById<ImageView>(R.id.img_makanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_barangsnack, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nama.text = data[position].nama
        holder.tv_kategori.text = data[position].kategori
        holder.tv_harga.text = Helper().gantiRupiah(data[position].harga)
        val image = Config.productUrl2 + data[position].gambar
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .into(holder.img_makanan)

    }

    override fun getItemCount(): Int {
        return data.size
    }

}
