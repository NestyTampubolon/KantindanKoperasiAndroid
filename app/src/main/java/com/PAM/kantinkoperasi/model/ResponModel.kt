package com.PAM.kantinkoperasi.model

class ResponModel {
    var success = 0
    lateinit var message: String
    var user = User()
    var makananminumans: ArrayList<MakananMinuman> = ArrayList()
    var barangsnacks: ArrayList<BarangSnack> = ArrayList()

    var transaksis: ArrayList<PemesananMakananMinuman> = ArrayList()

    var transaksi = PemesananMakananMinuman()

}