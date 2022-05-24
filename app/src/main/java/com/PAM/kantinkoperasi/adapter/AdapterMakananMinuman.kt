package com.PAM.kantinkoperasi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.MakananActivity
import com.PAM.kantinkoperasi.MakananDetailActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.MakananMinuman
import com.PAM.kantinkoperasi.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AdapterMakananMinuman(var activity: MakananActivity, var data:ArrayList<MakananMinuman>): RecyclerView.Adapter<AdapterMakananMinuman.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_kategori = view.findViewById<TextView>(R.id.tv_kategori)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val img_makanan = view.findViewById<ImageView>(R.id.img_makanan)
        val layout = view.findViewById<CardView>(R.id.item_makananminuman)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_makananminuman, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nama.text = data[position].nama
        holder.tv_kategori.text = data[position].kategori
        holder.tv_harga.text = Helper().gantiRupiah(data[position].harga)
        val image = Config.productUrl + data[position].gambar
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .into(holder.img_makanan)

        holder.layout.setOnClickListener {
            val act = Intent(activity, MakananDetailActivity::class.java)
            val str = Gson().toJson(data[position], MakananMinuman::class.java)
            act.putExtra("extra", str)
            activity.startActivity(act)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}