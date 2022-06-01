package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BookingRuangan
import com.PAM.kantinkoperasi.model.PemesananPulsa
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatPemesananPulsaDetail : AppCompatActivity() {
    private lateinit var tv_status : TextView
    private lateinit var tv_kode_transaksi : TextView
    private lateinit var tv_tanggal_pemesanan : TextView
    private lateinit var tv_nomor_telephone : TextView
    private lateinit var tv_jumlah_pulsa : TextView
    private lateinit var tv_total_pembayaran : TextView
    private lateinit var btn_batal: Button

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
        btn_batal = findViewById(R.id.btn_batal)

        val json = intent.getStringExtra("transaksi")
        pemesananpulsa = Gson().fromJson(json, PemesananPulsa::class.java)

        setData(pemesananpulsa)
        btn_batal.setOnClickListener {
            batal(pemesananpulsa)
        }
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

        var color = getColor(R.color.colorPrimary)
        if  (t.status == "PERMINTAAN") color = getColor(R.color.purple_500)
        else if (t.status == "VERIFIKASI") color = getColor(R.color.teal_700)
        else if (t.status == "TERIMA") color = getColor(R.color.hijauNeon)

        tv_status.setTextColor(color)
    }

    fun batal(t: PemesananPulsa){
        ApiConfig.instanceRetrofit.deletePemesananPulsa(Integer.valueOf(t.id_pemesanan_pulsa)).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RiwayatPemesananPulsaDetail, "Pemesanan Pulsa berhasil dihapus" , Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RiwayatPemesananPulsaDetail, RiwayatActivity::class.java))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}