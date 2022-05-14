package com.PAM.kantinkoperasi.helper

import java.text.NumberFormat
import java.util.*

class Helper {
    fun gantiRupiah(value: Int): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(value)
    }
}