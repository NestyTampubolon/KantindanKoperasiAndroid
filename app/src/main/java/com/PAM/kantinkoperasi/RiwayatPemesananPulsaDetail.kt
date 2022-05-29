package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BookingRuangan
import com.PAM.kantinkoperasi.model.PemesananPulsa
import com.google.gson.Gson

class RiwayatPemesananPulsaDetail : AppCompatActivity() {
    private lateinit var tv_status : TextView
    private lateinit var tv_kode_transaksi : TextView
    private lateinit var tv_tanggal_pemesanan : TextView
    private lateinit var tv_nomor_telephone : TextView
    private lateinit var tv_jumlah_pulsa : TextView
    private lateinit var tv_total_pembayaran : TextView

    var pemesananpulsa = PemesananPulsa()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_pemesanan_pulsa_detail)

        tv_status = findViewById(R.id.tv_status)
        tv_kode_transaksi = findViewById(R.id.tv_kode_transaksi)
        tv_tanggal_pemesanan = findViewById(R.id.tv_tanggal_pemesanan)
        tv_nomor_telephone = findViewById(R.id.tv_nomor_telephone)
        tv_jumlah_pulsa = findViewById(R.id.tv_jumlah_pulsa)
        tv_total_pembayaran = findViewById(R.id.tv_total_pembayaran)

        val json = intent.getStringExtra("transaksi")
        pemesananpulsa = Gson().fromJson(json, PemesananPulsa::class.java)

        setData(pemesananpulsa)
    }

    fun setData(t: PemesananPulsa) {
        tv_status.text = t.status
        tv_kode_transaksi.text = t.kode_transaksi
        tv_tanggal_pemesanan.text = t.tanggal_pemesanan
        tv_nomor_telephone.text = t.nomor_telephone
        tv_jumlah_pulsa.text = t.pulsa.nama
        tv_total_pembayaran.text = Helper().gantiRupiah(t.pulsa.harga)

        val formatBaru = "dd MMMM yyyy"
        tv_tanggal_pemesanan.text = Helper().convertTanggal(t.tanggal_pemesanan, formatBaru)



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