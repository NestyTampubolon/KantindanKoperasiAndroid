package com.PAM.kantinkoperasi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "keranjang_barang_snack")
public class BarangSnack implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_keranjang")
    public int id_keranjang;

    public int id_barang_snack;
    public String nama;
    public int harga;
    public String kategori;
    public int stok;
    public String gambar;

    public int jumlah = 1;
    public boolean selected = true;

}