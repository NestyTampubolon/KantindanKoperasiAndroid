package com.PAM.kantinkoperasi.model

class ResponModel {
    var success = 0
    lateinit var message: String
    var user = User()
    var makananminumans: ArrayList<MakananMinuman> = ArrayList()
    var barangsnacks: ArrayList<BarangSnack> = ArrayList()

    var pulsa:ArrayList<Pulsa> = ArrayList()

    var pemesananmakananminumans : ArrayList<PemesananMakananMinuman> = ArrayList()
    var pemesananmakananminuman = PemesananMakananMinuman()

    var pemesananbarangsnacks : ArrayList<PemesananBarangSnack> = ArrayList()
    var pemesananbarangsnack = PemesananBarangSnack()

    var bookings : ArrayList<BookingRuangan> = ArrayList()
    var booking = BookingRuangan()

    var pemesananpulsas : ArrayList<PemesananPulsa> = ArrayList()
    var pemesananpulsa = PemesananPulsa()



}