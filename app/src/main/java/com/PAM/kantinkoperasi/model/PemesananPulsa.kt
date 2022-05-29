package com.PAM.kantinkoperasi.model

class PemesananPulsa {
    lateinit var id_pemesanan_pulsa: String
    lateinit var kode_transaksi: String
    lateinit var tanggal_pemesanan: String
    lateinit var status: String
    lateinit var nomor_telephone: String
    lateinit var id_pulsa: String
    var pulsa = Pulsa()

}