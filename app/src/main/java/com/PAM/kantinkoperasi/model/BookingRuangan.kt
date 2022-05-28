package com.PAM.kantinkoperasi.model

class BookingRuangan {
    lateinit var id_booking: String
    lateinit var kode_booking: String
    lateinit var id_user : String
    lateinit var tanggal_pemesanan : String
    lateinit var nama_ruangan : String
    lateinit var jam_mulai : String
    lateinit var jam_selesai : String
    lateinit var deskripsi : String
    lateinit var status : String
    var updated_at = ""
    var created_at = ""
}