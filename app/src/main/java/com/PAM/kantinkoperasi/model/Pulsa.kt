package com.PAM.kantinkoperasi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pulsa")
class Pulsa : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "")
    var id_pulsa = 0
    var nominal: String? = null
    var harga = 0
}