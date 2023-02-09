package com.example.penjualan_adzinadami.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tb_Barang (
    @PrimaryKey
    val id_barang: Int,
    val nama_barang: String,
    val harga_barang: Int,
    val stok_barang: Int

        )
