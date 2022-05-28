package com.PAM.kantinkoperasi.model

class PemesananBarangSnack {
    var id_pemesanan_barang_snack = 0
    var kode_transaksi = ""
    var tanggal_pemesanan_barang_snack = ""
    var id_user = ""
    var total_item = ""
    var total_pembayaran = ""
    var nama_penerima = ""
    var nomor_telephone = ""
    var catatan = ""
    var status = ""
    val details = ArrayList<PemesananBarangSnackDetail>()
}