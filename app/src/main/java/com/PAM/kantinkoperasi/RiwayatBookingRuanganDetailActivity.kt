package com.PAM.kantinkoperasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananMakananMinumanDetail
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.BookingRuangan
import com.PAM.kantinkoperasi.model.PemesananMakananMinuman
import com.PAM.kantinkoperasi.model.PemesananMakananMinumanDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat_makanan_minuman_detail.*
import kotlinx.android.synthetic.main.item_riwayat.*
import okhttp3.ResponseBody
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatBookingRuanganDetailActivity : AppCompatActivity() {

    private lateinit var tv_status : TextView
    private lateinit var tv_kode_booking : TextView
    private lateinit var tv_tanggal_pemesanan :TextView
    private lateinit var tv_nama_ruangan : TextView
    private lateinit var tv_jam_mulai :TextView
    private lateinit var tv_jam_selesai : TextView
    private lateinit var tv_deskripsi : TextView
    private lateinit var btn_batal: Button

    var bookingruangan = BookingRuangan()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_booking_ruangan_detail)

        tv_status = findViewById(R.id.tv_status)
        tv_kode_booking = findViewById(R.id.tv_kode_booking)
        tv_tanggal_pemesanan = findViewById(R.id.tv_tanggal_pemesanan)
        tv_nama_ruangan = findViewById(R.id.tv_nama_ruangan)
        tv_jam_mulai = findViewById(R.id.tv_jam_mulai)
        tv_jam_selesai = findViewById(R.id.tv_jam_selesai)
        tv_deskripsi = findViewById(R.id.tv_deskripsi)
        btn_batal = findViewById(R.id.btn_batal)

        val json = intent.getStringExtra("transaksi")
        bookingruangan = Gson().fromJson(json, BookingRuangan::class.java)

        setData(bookingruangan)
        btn_batal.setOnClickListener {
            batal(bookingruangan)
        }
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


        var color = getColor(R.color.colorPrimary)
        if  (t.status == "PERMINTAAN") color = getColor(R.color.purple_500)
        else if (t.status == "VERIFIKASI") color = getColor(R.color.teal_700)
        else if (t.status == "TERIMA") color = getColor(R.color.hijauNeon)

        tv_status.setTextColor(color)
    }

    fun batal(t: BookingRuangan){
        ApiConfig.instanceRetrofit.deleteBookingRuangan(Integer.valueOf(t.id_booking)).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RiwayatBookingRuanganDetailActivity, "Booking Ruangan berhasil dihapus" , Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RiwayatBookingRuanganDetailActivity, RiwayatActivity::class.java))
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