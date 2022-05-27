package com.PAM.kantinkoperasi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.PAM.kantinkoperasi.app.ApiConfig
import com.PAM.kantinkoperasi.helper.SharedPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BookingRuanganActivity : AppCompatActivity(){

    private lateinit var TanggalPesan : EditText
    private lateinit var NamaRuangan : EditText
    private lateinit var JamMulai : EditText
    private lateinit var JamSelesai: EditText
    private lateinit var Deskripsi : EditText
    private lateinit var ButtonBatal : Button
    private lateinit var ButtonPesan : Button

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan_ruangan)

        TanggalPesan = findViewById(R.id.edt_tanggal_pemesanan)
        NamaRuangan = findViewById(R.id.edt_nama_ruangan)
        JamMulai = findViewById(R.id.edt_jam_mulai)
        JamSelesai = findViewById(R.id.edt_jam_selesai)
        Deskripsi = findViewById(R.id.edt_deskripsi)
       // ButtonBatal = findViewById(R.id.btn_batal)
        ButtonPesan = findViewById(R.id.btn_pesan)
        Pesan()
    }

    fun Pesan(){
        ButtonPesan.setOnClickListener {
            val tanggalPesan = TanggalPesan.text.toString().trim()
            val namaRuangan = NamaRuangan.text.toString().trim()
            val jamMulai = JamMulai.text.toString().trim()
            val jamSelesai = JamSelesai.text.toString().trim()
            val deskripsi = Deskripsi.text.toString().trim()

            var kolomKosong = false

            if(tanggalPesan.isEmpty()){
                kolomKosong = true
                NamaRuangan.error = "Field ini tidak boleh kosong"
            }
            if(namaRuangan.isEmpty()){
                kolomKosong = true
                NamaRuangan.error = "Field ini tidak boleh kosong"
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

            TanggalPesan.setText("")
            NamaRuangan.setText("")
            JamMulai.setText("")
            JamSelesai.setText("")
            Deskripsi.setText("")

            val user = SharedPref(this).getUser()!!

            ApiConfig.instanceRetrofit.bookingruangan( user.id_user.toString(),tanggalPesan,namaRuangan,jamMulai,jamSelesai,deskripsi).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Toast.makeText(this@BookingRuanganActivity, "Bookingan Anda Sedang Diproses", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }

            })
        }

    }

    fun withCalender(view: View) {
        var cal = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day =  cal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, i, mMon, i3 ->
            TanggalPesan.setText("" + i3 + "/" + mMon + "/" + i)
        },year, month, day)
        dpd.show()
    }

    fun withClockStart(view: View) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                JamMulai.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)
        mTimePicker.show()
    }

    fun withClockFinish(view: View) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                JamSelesai.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)
        mTimePicker.show()
    }
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }

    fun withItemsNamaRuangan(view: View) {
        val items = arrayOf("Kantin 1 Lantai 1", "Kantin 1 Lantai 2", "Kantin 2 Lantai 1", "Kantin 2 Lantai 2")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Daftar Prodi")
            setItems(items) { dialog, which ->
                NamaRuangan.setText(items[which] )
            }
            setPositiveButton("OK", positiveButtonClick)
            show()
        }
    }

//    fun Batal(v: View){
//
//        TanggalPesan.setText("")
//        NamaRuangan.setText("")
//        JamMulai.setText("")
//        JamSelesai.setText("")
//        Deskripsi.setText("")
//        ButtonBatal.setText("")
//        ButtonPesan.setText("")
//        Toast.makeText(this, "Batal", Toast.LENGTH_SHORT).show()
//    }
}

