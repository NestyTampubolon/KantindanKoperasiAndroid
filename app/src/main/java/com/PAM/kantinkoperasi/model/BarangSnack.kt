package com.PAM.kantinkoperasi.model

import java.io.Serializable
class BarangSnack : Serializable {
    lateinit var id_barang_snack : String
    lateinit var nama: String
    var harga: Int = 0
    lateinit var kategori: String
    var stok : Int = 0
    lateinit var gambar : String
}