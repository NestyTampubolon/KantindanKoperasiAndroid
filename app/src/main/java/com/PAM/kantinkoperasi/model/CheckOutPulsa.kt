package com.PAM.kantinkoperasi.model

class CheckOutPulsa {
    lateinit var id_user: String
    lateinit var total_pembayaran: String
    lateinit var nomor_telephone: String
    lateinit var nominal: String
    lateinit var pembayaran: String

    var pulsa = ArrayList<Item>()

    class Item {
        lateinit var id_pulsa: String
        lateinit var nominal: String
        lateinit var total_harga: String
    }
}