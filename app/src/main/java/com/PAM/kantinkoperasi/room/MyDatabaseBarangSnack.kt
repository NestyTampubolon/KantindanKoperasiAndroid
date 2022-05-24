package com.PAM.kantinkoperasi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.PAM.kantinkoperasi.model.BarangSnack

@Database(entities = [BarangSnack::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabaseBarangSnack : RoomDatabase(){
    abstract fun daoBarangSnack(): DaoBarangSnack

    companion object{
        private var INSTANCE: MyDatabaseBarangSnack? = null

        fun getInstance(context: Context): MyDatabaseBarangSnack? {
            if (INSTANCE == null) {
                synchronized(MyDatabaseBarangSnack::class) {
                    MyDatabaseBarangSnack.INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabaseBarangSnack::class.java, "MyDatabaseName3" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}