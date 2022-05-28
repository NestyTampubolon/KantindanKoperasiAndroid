package com.PAM.kantinkoperasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananBarangSnackDetail
import com.PAM.kantinkoperasi.adapter.AdapterRiwayatPemesananMakananMinumanDetail
import com.PAM.kantinkoperasi.helper.Helper
import com.PAM.kantinkoperasi.model.PemesananBarangSnack
import com.PAM.kantinkoperasi.model.PemesananBarangSnackDetail
import com.PAM.kantinkoperasi.model.PemesananMakananMinuman
import com.PAM.kantinkoperasi.model.PemesananMakananMinumanDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat_makanan_minuman_detail.*

class RiwayatBarangSnackDetail : AppCompatActivity() {

    var pemesananbarangsnack = PemesananBarangSnack()
    private lateinit var tv_status : TextView
    private lateinit var tv_tanggal_pembayaran : TextView
    private lateinit var tv_nama_penerima : TextView
    private lateinit var tv_total_pembayaran : TextView
    private lateinit var tv_nomor_telephone: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_barang_snack_detail)

        tv_status = findViewById(R.id.tv_status)
        tv_tanggal_pembayaran = findViewById(R.id.tv_tanggal_pembayaran)
        tv_nama_penerima = findViewById(R.id.tv_nama_penerima)
        tv_total_pembayaran = findViewById(R.id.tv_total_pembayaran)
        tv_nomor_telephone = findViewById(R.id.tv_nomor_telephone)

        val json = intent.getStringExtra("transaksi")
        pemesananbarangsnack = Gson().fromJson(json, PemesananBarangSnack::class.java)

        setData(pemesananbarangsnack)
        displayProduk(pemesananbarangsnack.details)
    }

    fun setData(t: PemesananBarangSnack) {
        tv_status.text = t.status

        val formatBaru = "dd MMMM yyyy"
        tv_tanggal_pembayaran.text = Helper().convertTanggal(t.tanggal_pemesanan_barang_snack, formatBaru)

        tv_nama_penerima.text = t.nama_penerima
        tv_nomor_telephone.text = t.nomor_telephone
        tv_total_pembayaran.text = Helper().gantiRupiah(Integer.valueOf(t.total_pembayaran))


//        var color = getColor(R.color.menungu)
//        if (t.status == "SELESAI") color = getColor(R.color.selesai)
//        else if (t.status == "BATAL") color = getColor(R.color.batal)

//        tv_status.setTextColor(color)
    }

    fun displayProduk(pemesananbarangsnacks: ArrayList<PemesananBarangSnackDetail>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterRiwayatPemesananBarangSnackDetail(pemesananbarangsnacks)
        rv_produk.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}