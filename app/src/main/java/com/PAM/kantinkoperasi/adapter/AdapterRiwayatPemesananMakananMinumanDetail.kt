package com.PAM.kantinkoperasi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.RiwayatMakananMinumanDetail
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.PemesananMakananMinumanDetail
import com.PAM.kantinkoperasi.util.Config
import com.squareup.picasso.Picasso

class AdapterRiwayatPemesananMakananMinumanDetail(var data: ArrayList<PemesananMakananMinumanDetail>) : RecyclerView.Adapter<AdapterRiwayatPemesananMakananMinumanDetail.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTotalHarga = view.findViewById<TextView>(R.id.tv_totalHarga)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_makanan, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.makananminuman.nama
        val p = a.makananminuman
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(p.harga)
        holder.tvTotalHarga.text = Helper().gantiRupiah(a.total_harga)
        holder.tvJumlah.text = a.kuantitas.toString() + " Items"

        holder.layout.setOnClickListener {
//            listener.onClicked(a)
        }

        val image = Config.productUrl + p.gambar
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.img_makanan)
            .error(R.drawable.img_makanan)
            .into(holder.imgProduk)
    }

    interface Listeners {
        fun onClicked(data: PemesananMakananMinumanDetail)
    }

}