package com.PAM.kantinkoperasi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "pembayaran_pulsa")
public class Pulsa_k implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_pembayaran")
    public int id_Pembayaran;
    public int id_pulsa;
    public String nominal;
    public int harga;
    public  int nomor_telepon;
    public boolean selected = true;

}