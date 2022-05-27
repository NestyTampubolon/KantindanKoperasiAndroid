package com.PAM.kantinkoperasi.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.PAM.kantinkoperasi.MainActivity
import com.PAM.kantinkoperasi.R
import com.PAM.kantinkoperasi.helper.SharedPref


class AkunFragment : Fragment(R.layout.fragment_akun) {

    lateinit var tv_nama : TextView
    lateinit var tv_noHandphone: TextView
    lateinit var tv_email : TextView

    lateinit var s:SharedPref

    lateinit var btn_riwayat : RelativeLayout
    lateinit var btn_logout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = SharedPref(requireActivity())

        mainButton()
        setData()
        return view
    }

    fun mainButton() {
        btn_logout.setOnClickListener {
            s.setStatusLogin(false)
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }

        btn_riwayat.setOnClickListener {
            //startActivity(Intent(requireActivity(), RiwayatActivity::class.java))
        }
    }

    fun setData() {

        if (s.getUser() == null) {
            return
        }

        val user = s.getUser()!!


        tv_noHandphone.text = user.noHandphone
        tv_email.text = user.email
        tv_nama.text = user.name
    }

    private fun init(view: View){
        tv_nama = view.findViewById(R.id.tv_nama)
        tv_noHandphone = view.findViewById(R.id.tv_noHandphone)
        tv_email = view.findViewById(R.id.tv_email)

        btn_riwayat = view.findViewById(R.id.btn_riwayat)
        btn_logout = view.findViewById(R.id.btn_logout)
    }
}