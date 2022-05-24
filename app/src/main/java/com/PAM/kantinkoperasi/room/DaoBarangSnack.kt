package com.PAM.kantinkoperasi.room

import android.text.TextUtils.isEmpty
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.PAM.kantinkoperasi.model.BarangSnack

@Dao
interface DaoBarangSnack {
    @Insert(onConflict = REPLACE)
    fun insert(data : BarangSnack)

    @Delete
    fun delete(data: BarangSnack)

    @Delete
    fun delete(data: List<BarangSnack>)

    @Update
    fun update(data: BarangSnack): Int

    @Query("SELECT * from keranjang_barang_snack ORDER BY id_keranjang ASC")
    fun getAll(): List<BarangSnack>

    @Query("SELECT * FROM keranjang_barang_snack WHERE id_barang_snack = :id LIMIT 1")
    fun getBarangSnack(id: Int): BarangSnack?

    @Query("DELETE FROM keranjang_barang_snack WHERE id_barang_snack = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang_barang_snack")
    fun deleteAll(): Int

}
