package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananMakananMinumanDetail
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BookingRuangan
import com.PAM.kantinkoperasi.model.PemesananMakananMinuman
import com.PAM.kantinkoperasi.model.PemesananMakananMinumanDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat_makanan_minuman_detail.*
import kotlinx.android.synthetic.main.item_riwayat.*
import org.w3c.dom.Text

class RiwayatBookingRuanganDetailActivity : AppCompatActivity() {

    private lateinit var tv_status : TextView
    private lateinit var tv_tanggal : TextView
    private lateinit var tv_kode_booking : TextView
    private lateinit var tv_tanggal_pemesanan :TextView
    private lateinit var tv_nama_ruangan : TextView
    private lateinit var tv_jam_mulai :TextView
    private lateinit var tv_jam_selesai : TextView
    private lateinit var tv_deskripsi : TextView

    var bookingruangan = BookingRuangan()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_booking_ruangan_detail)

        tv_status = findViewById(R.id.tv_status)
        tv_tanggal = findViewById(R.id.tv_tanggal)
        tv_kode_booking = findViewById(R.id.tv_kode_booking)
        tv_tanggal_pemesanan = findViewById(R.id.tv_tanggal_pemesanan)
        tv_nama_ruangan = findViewById(R.id.tv_nama_ruangan)
        tv_jam_mulai = findViewById(R.id.tv_jam_mulai)
        tv_jam_selesai = findViewById(R.id.tv_jam_selesai)
        tv_deskripsi = findViewById(R.id.tv_deskripsi)

        val json = intent.getStringExtra("transaksi")
        bookingruangan = Gson().fromJson(json, BookingRuangan::class.java)

        setData(bookingruangan)
    }

    fun setData(t: BookingRuangan) {
        tv_status.text = t.status
        tv_kode_booking.text = t.kode_booking
        tv_nama_ruangan.text = t.nama_ruangan
        tv_jam_mulai.text = t.jam_mulai
        tv_jam_selesai.text = t.jam_selesai
        tv_deskripsi.text = t.deskripsi

        val formatBaru = "dd MMMM yyyy"
        tv_tanggal_pemesanan.text = Helper().convertTanggal2(t.tanggal_pemesanan, formatBaru)



//        var color = getColor(R.color.menungu)
//        if (t.status == "SELESAI") color = getColor(R.color.selesai)
//        else if (t.status == "BATAL") color = getColor(R.color.batal)

//        tv_status.setTextColor(color)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}