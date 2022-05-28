package com.PAM.kantinkoperasi.model

class CheckoutBarang {

        lateinit var id_user: String
        lateinit var total_item: String
        lateinit var total_pembayaran: String
        lateinit var nama_penerima: String
        lateinit var nomor_telephone: String
        lateinit var catatan: String
        var produks = ArrayList<Item>()

        class Item {
            lateinit var id_barang_snack : String
            lateinit var kuantitas: String
            lateinit var total_harga: String
        }
}