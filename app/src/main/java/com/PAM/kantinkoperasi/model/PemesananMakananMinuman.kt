package com.PAM.kantinkoperasi.model

class PemesananMakananMinuman {
    var id_pemesanan_makanan_minuman = 0
    var kode_transaksi = ""
    var tanggal_pemesanan_makanan_minuman = ""
    var id_user = ""
    var total_item = ""
    var total_pembayaran = ""
    var nama_penerima = ""
    var nomor_telephone = ""
    var catatan = ""
    var status = ""
    val details = ArrayList<PemesananMakananMinumanDetail>()
}