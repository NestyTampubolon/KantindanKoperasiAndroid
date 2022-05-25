package com.PAM.kantinkoperasi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class BookingRuanganActivity : AppCompatActivity(){

    private lateinit var TanggalPesan : EditText
    private lateinit var NamaRuangan : EditText
    private lateinit var NamaPemesan : EditText
    private lateinit var JamMulai : EditText
    private lateinit var JamSelesai: EditText
    private lateinit var Deskripsi : EditText
    private lateinit var Status : EditText
    private lateinit var ButtonBatal : Button
    private lateinit var ButtonPesan : Button

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan_ruangan)

        TanggalPesan = findViewById(R.id.edt_tanggal_pemesanan)
        NamaRuangan = findViewById(R.id.edt_nama_ruangan)
        NamaPemesan = findViewById(R.id.edt_nama_pemesan)
        JamMulai = findViewById(R.id.edt_jam_mulai)
        JamSelesai = findViewById(R.id.edt_jam_selesai)
        Deskripsi = findViewById(R.id.edt_deskripsi)
        Status = findViewById(R.id.edt_status)
        ButtonBatal = findViewById(R.id.btn_batal)
        ButtonPesan = findViewById(R.id.btn_pesan)
    }

    fun Pesan(v : View?){
        val tanggalPesan = TanggalPesan.text.toString().trim()
        val namaRuangan = NamaRuangan.text.toString().trim()
        val namaPemesan = NamaPemesan.text.toString().trim()
        val jamMulai = JamMulai.text.toString().trim()
        val jamSelesai = JamSelesai.text.toString().trim()
        val deskripsi = Deskripsi.text.toString().trim()
        val status = Status.text.toString().trim()

        var kolomKosong = false

        if(tanggalPesan.isEmpty()){
            kolomKosong = true
            NamaRuangan.error = "Field ini tidak boleh kosong"
        }
        if(namaRuangan.isEmpty()){
            kolomKosong = true
            NamaRuangan.error = "Field ini tidak boleh kosong"
        }
        if(namaPemesan.isEmpty()){
            kolomKosong = true
            NamaPemesan.error = "Field ini tidak boleh kosong"
        }
        if(jamMulai.isEmpty()){
            kolomKosong = true
            JamMulai.error = "Field ini tidak boleh kosong"
        }
        if(jamSelesai.isEmpty()){
            kolomKosong = true
            JamSelesai.error = "Field ini tidak boleh kosong"
        }
        if(deskripsi.isEmpty()){
            kolomKosong = true
            Deskripsi.error = "Field ini tidak boleh kosong"
        }
        if(status.isEmpty()){
            kolomKosong = true
            Status.error = "Field ini tidak boleh kosong"
        }

        TanggalPesan.setText("")
        NamaRuangan.setText("")
        NamaPemesan.setText("")
        JamMulai.setText("")
        JamSelesai.setText("")
        Deskripsi.setText("")
        Status.setText("")
        ButtonBatal.setText("")
        ButtonPesan.setText("")
        if(!kolomKosong){
            Toast.makeText(this, "Pemesanan Anda Sedang Diproses", Toast.LENGTH_SHORT).show()
        }
    }

    fun Batal(v: View){

        TanggalPesan.setText("")
        NamaRuangan.setText("")
        NamaPemesan.setText("")
        JamMulai.setText("")
        JamSelesai.setText("")
        Deskripsi.setText("")
        Status.setText("")
        ButtonBatal.setText("")
        ButtonPesan.setText("")
        Toast.makeText(this, "Batal", Toast.LENGTH_SHORT).show()
    }
}

