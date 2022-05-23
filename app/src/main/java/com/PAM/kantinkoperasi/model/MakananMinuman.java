package com.PAM.kantinkoperasi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "keranjang_makanan_minuman")
public class MakananMinuman implements Serializable{
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "id_keranjang")
    public int id_keranjang;

    public int id_makanan_minuman;
    public String nama;
    public int harga;
    public String kategori;
    public int stok;
    public String gambar;

    public int jumlah = 1;
    public boolean selected = true;

}
