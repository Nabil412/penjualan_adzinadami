package com.example.penjualan_adzinadami

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_adzinadami.room.Constant
import com.example.penjualan_adzinadami.room.dbpenjualan
import com.example.penjualan_adzinadami.room.tb_Barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.withTestContext
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var barangAdapter: BarangAdapter
    val db by lazy {dbpenjualan(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pindah()
        setupRecyclerview()
    }

    override fun onStart() {
        super.onStart()
        loadData()

    }
    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.TbgoodsDAO().tampilsemua()
            Log.d("MainActivity", "dbResponse:$barang")
            withContext(Dispatchers.Main){
                barangAdapter.setData(barang)
            }
        }

    }

    private fun pindah(){
        btnInput.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbbrgid:Int, intentType:Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_nis",tbbrgid)
                .putExtra("intent_type",intentType)
        )
    }

    fun setupRecyclerview(){
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.onAdapterListener{

            override fun onClick(tbbar: tb_Barang) {
                intentEdit(tbbar.id_barang,Constant.TYPE_READ)
            }

            override fun onUpdate(tbbar: tb_Barang) {
                intentEdit(tbbar.id_barang,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbbar: tb_Barang) {
                deleteDialog(tbbar)
            }
        } )

        //ID RICYCLERVIEW
        listdatabarang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }
    private fun deleteDialog(tbbarang: tb_Barang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("KONFIRMASI")
            setMessage("YAKIN HAPUS ${tbbarang.id_barang}")
            setNegativeButton("BATAL") {dialogInterface, i  ->
                dialogInterface.dismiss()
            }
            setPositiveButton("HAPUS") {dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.TbgoodsDAO().deletetbbarang(tbbarang)
                    loadData()
                }
            }

        }
        alertDialog.show()
    }

}