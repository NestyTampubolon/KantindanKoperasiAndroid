package com.PAM.kantinkoperasi.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.PAM.kantinkoperasi.model.MakananMinuman

@Dao
interface DaoKeranjangMakananMinuman {

    @Insert(onConflict = REPLACE)
    fun insert(data: MakananMinuman)

    @Delete
    fun delete(data: MakananMinuman)

    @Delete
    fun delete(data: List<MakananMinuman>)

    @Update
    fun update(data: MakananMinuman): Int

    @Query("SELECT * from keranjang_makanan_minuman ORDER BY id_keranjang ASC")
    fun getAll(): List<MakananMinuman>

    @Query("SELECT * FROM keranjang_makanan_minuman WHERE id_makanan_minuman = :id LIMIT 1")
    fun getMakananMinuman(id: Int): MakananMinuman?

    @Query("DELETE FROM keranjang_makanan_minuman WHERE id_makanan_minuman = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang_makanan_minuman")
    fun deleteAll(): Int
}