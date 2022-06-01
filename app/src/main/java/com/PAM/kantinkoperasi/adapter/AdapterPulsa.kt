package com.PAM.kantinkoperasi.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.*
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.Pulsa
import com.google.gson.Gson

class AdapterPulsa(var activity:PulsaActivity, var data:ArrayList<Pulsa>): RecyclerView.Adapter<AdapterPulsa.Holder>(){

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tv_nominal = view.findViewById<TextView>(R.id.tv_nominal)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)
        val layout = view.findViewById<CardView>(R.id.item_pulsa)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pulsa, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nominal.text = data[position].nama
        holder.tv_harga.text = Helper().gantiRupiah(data[position].harga)

        holder.layout.setOnClickListener{
            val act = Intent(activity, Pembelian_Pulsa::class.java)
            val str = Gson().toJson(data[position], Pulsa::class.java)
            act.putExtra("extra", str)
            activity.startActivity(act)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
