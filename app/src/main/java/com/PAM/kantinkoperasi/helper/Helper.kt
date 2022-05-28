package com.PAM.kantinkoperasi.helper

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    fun gantiRupiah(value: Int): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
    }
    fun convertTanggal(tgl: String, formatBaru: String, fromatLama: String = "yyyy-MM-dd") :String{
        val dateFormat = SimpleDateFormat(fromatLama)
        val confert = dateFormat.parse(tgl)
        dateFormat.applyPattern(formatBaru)
        return dateFormat.format(confert)
    }


    fun convertTanggal2(tgl: String, formatBaru: String, fromatLama: String = "dd/MM/yyyy") :String{
        val dateFormat = SimpleDateFormat(fromatLama)
        val confert = dateFormat.parse(tgl)
        dateFormat.applyPattern(formatBaru)
        return dateFormat.format(confert)
    }
}