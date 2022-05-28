package com.PAM.kantinkoperasi.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.PAM.kantinkoperasi.*
import com.PAM.kantinkoperasi.helper.SharedPref


class KeranjangFragment : Fragment(R.layout.fragment_keranjang) {
    lateinit var linearLayout1 : LinearLayout
    lateinit var linearLayout2 : LinearLayout
    lateinit var linearLayout3 : LinearLayout

//    lateinit var text3 : TextView
//    lateinit var btn_riwayat : RelativeLayout

    lateinit var s: SharedPref
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        s = SharedPref(requireActivity())

        mainButton()
//        setData()
        return view
    }

    fun mainButton() {
        linearLayout1.setOnClickListener {
            val intent = Intent(activity, KeranjangMakanan::class.java)
            startActivity(intent)
        }

        linearLayout2.setOnClickListener {
            val intent = Intent(activity, KeranjangBarangSnack::class.java)
            startActivity(intent)
        }
//        linearLayout3.setOnClickListener {
//            val intent = Intent(activity, Keranjang_Pulsa::class.java)
//            startActivity(intent)
//        }

    }

//    fun setData() {
//
//        if (s.getUser() == null) {
//            return
//        }
//
//        val user = s.getUser()!!
//
//
//        text3.text = user.name
//    }

    private fun init(view: View){
        linearLayout1 = view.findViewById(R.id.linearLayout1)
        linearLayout2 = view.findViewById(R.id.linearLayout2)
        linearLayout3 = view.findViewById(R.id.linearLayout3)
    }
}