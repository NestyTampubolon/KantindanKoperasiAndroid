package com.PAM.kantinkoperasi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.PAM.kantinkoperasi.model.Pulsa_k

@Database(entities = [Pulsa_k::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabasePulsa : RoomDatabase(){
    abstract fun daoPulsa(): DaoPulsa

    companion object{
        private var INSTANCE: MyDatabasePulsa? = null

        fun getInstance(context: Context): MyDatabasePulsa? {
            if (INSTANCE == null) {
                synchronized(MyDatabasePulsa::class) {
                    MyDatabasePulsa.INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabasePulsa::class.java, "MyDatabaseName4" // Database Name
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