package com.PAM.kantinkoperasi.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.PulsaActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.Pulsa

class AdapterPulsa(var activity:PulsaActivity, var data:ArrayList<Pulsa>): RecyclerView.Adapter<AdapterPulsa.Holder>(){

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val tv_nominal = view.findViewById<TextView>(R.id.tv_nominal)
        val tv_harga = view.findViewById<TextView>(R.id.tv_harga)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pulsa, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tv_nominal.text = data[position].nominal
        holder.tv_harga.text = data[position].harga.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
