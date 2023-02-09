package com.example.penjualan_adzinadami.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities= [tb_Barang::class],
    version = 1
)
abstract class dbpenjualan : RoomDatabase() {
    abstract fun TbgoodsDAO() : Tbbarang_DAO

    companion object{
        @Volatile private var instance: dbpenjualan? = null
        private val LOCK = Any ()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: builDatabase(context).also{
                instance =it
            }
        }
        private fun builDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                dbpenjualan::class.java,
                "12345db").build()


        }
    }
