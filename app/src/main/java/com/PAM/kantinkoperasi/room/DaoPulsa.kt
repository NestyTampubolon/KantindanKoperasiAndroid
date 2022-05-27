package com.PAM.kantinkoperasi.room

import android.text.TextUtils.isEmpty
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.PAM.kantinkoperasi.model.Pulsa_k

@Dao
interface DaoPulsa {
    @Insert(onConflict = REPLACE)
    fun insert(data : Pulsa_k)

    @Delete
    fun delete(data: Pulsa_k)

    @Delete
    fun delete(data: List<Pulsa_k>)

    @Update
    fun update(data: Pulsa_k): Int

    @Query("SELECT * from pembayaran_pulsa ORDER BY id_pembayaran ASC")
    fun getAll(): List<Pulsa_k>

    @Query("SELECT * FROM pembayaran_pulsa WHERE id_pulsa = :id LIMIT 1")
    fun getPulsa_k(id: Int): Pulsa_k?

    @Query("DELETE FROM pembayaran_pulsa WHERE id_pulsa = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM pembayaran_pulsa")
    fun deleteAll(): Int

}
