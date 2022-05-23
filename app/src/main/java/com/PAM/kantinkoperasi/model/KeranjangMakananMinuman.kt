package com.PAM.kantinkoperasi.model

import java.io.Serializable
class KeranjangMakananMinuman : Serializable {
    lateinit var id_keranjang_makanan_minuman: String
    lateinit var id_user : String
    lateinit var id_makanan_minuman : String
    var kuantitas: Int = 0
    lateinit var total: String
}


