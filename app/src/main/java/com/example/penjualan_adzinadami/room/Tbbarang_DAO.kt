package com.example.penjualan_adzinadami.room

import androidx.room.*

@Dao
interface Tbbarang_DAO {

    @Insert
    fun addtbbarang(barang: tb_Barang)

    @Update
    fun updatetbbarang(barang: tb_Barang)

    @Delete
    fun deletetbbarang(barang: tb_Barang)

    @Query("SELECT * FROM tb_Barang")
    fun tampilsemua (): List<tb_Barang>

    @Query("SELECT * FROM tb_Barang WHERE id_barang=:barang_id")
    fun tampilid(barang_id: Int): List<tb_Barang>

}