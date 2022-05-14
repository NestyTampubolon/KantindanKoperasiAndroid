package com.PAM.kantinkoperasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.MakananActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.MakananMinuman

class AdapterMakananMinuman(var activity: MakananActivity, var data:ArrayList<MakananMinuman>): RecyclerView.Adapter<AdapterMakananMinuman.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_kategori = view.findViewById<TextView>(R.id.tv_kategori)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_makananminuman, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nama.text = data[position].nama
        holder.tv_kategori.text = data[position].kategori
        holder.tv_harga.text = Helper().gantiRupiah(data[position].harga)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}