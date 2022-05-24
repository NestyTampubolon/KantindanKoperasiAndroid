package com.PAM.kantinkoperasi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.BarangActivity
import com.PAM.kantinkoperasi.BarangDetailActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BarangSnack
import com.google.gson.Gson

class AdapterBarangSnack(var activity: BarangActivity, var data:ArrayList<BarangSnack>): RecyclerView.Adapter<AdapterBarangSnack.Holder>(){

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_kategori = view.findViewById<TextView>(R.id.tv_kategori)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val layout = view.findViewById<CardView>(R.id.item_barangsnack)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_barangsnack, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nama.text = data[position].nama
        holder.tv_kategori.text = data[position].kategori
        holder.tv_harga.text = Helper().gantiRupiah(data[position].harga)

        holder.layout.setOnClickListener{
            val act = Intent(activity, BarangDetailActivity::class.java)
            val str = Gson().toJson(data[position], BarangSnack::class.java)
            act.putExtra("extra", str)
            activity.startActivity(act)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
