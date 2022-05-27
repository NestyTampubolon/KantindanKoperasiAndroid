package com.PAM.kantinkoperasi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.PemesananBarangSnack
import com.PAM.kantinkoperasi.model.PemesananMakananMinuman

class AdapterRiwayatPemesananBarangSnack(var data: ArrayList<PemesananBarangSnack>, var listener: Listeners) : RecyclerView.Adapter<AdapterRiwayatPemesananBarangSnack.Holder>()  {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_nama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val tv_tanggal = view.findViewById<TextView>(R.id.tv_tgl)
        val tv_jumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val tv_status = view.findViewById<TextView>(R.id.tv_status)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_barang, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.kode_transaksi
        holder.tv_nama.text = name
        holder.tv_harga.text = Helper().gantiRupiah(Integer.valueOf(a.total_pembayaran))
        holder.tv_jumlah.text = a.total_item + " Items"
        holder.tv_status.text = a.status

        // 2021-04-30 18:30:20 //24
        // jam 1   k || 01  kk
        // 09:20:20 am 12/pm/am
        val formatBaru = "d MMM yyyy"
        holder.tv_tanggal.text = Helper().convertTanggal(a.tanggal_pemesanan_barang_snack, formatBaru)
//        var color = context.getColor(R.color.menungu)
//        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
//        else if (a.status == "BATAL") color = context.getColor(R.color.batal)
//
//        holder.tvStatus.setTextColor(color)

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface Listeners {
        fun onClicked(data: PemesananBarangSnack)
    }
}