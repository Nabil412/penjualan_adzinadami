package com.example.penjualan_adzinadami

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_adzinadami.room.Constant
import com.example.penjualan_adzinadami.room.dbpenjualan
import com.example.penjualan_adzinadami.room.tb_Barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy {dbpenjualan(this)}
    private var tbbarid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        simpandata()

        tbbarid = intent.getIntExtra("intent_id",0)
        Toast.makeText(this,tbbarid.toString(),Toast.LENGTH_SHORT).show()
    }


    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                btnupdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btnSimpan.visibility = View.GONE
                btnupdate.visibility = View.GONE
                tampilbarang()
            }
            Constant.TYPE_UPDATE -> {
                btnSimpan.visibility = View.GONE
                tampilbarang()
            }
        }

    }

    private fun simpandata(){
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.TbgoodsDAO().addtbbarang(
                    tb_Barang(EtIdbrg.text.toString().toInt(),
                        EtNamabrg.text.toString(),
                        EtHargabrg.text.toString().toInt(),
                        EtStokbrg.text.toString().toInt())
                    )
                finish()
            }
        }
        //menambahkan tombol update
        btnupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.TbgoodsDAO().updatetbbarang(
                    tb_Barang(tbbarid,
                        EtNamabrg.text.toString(),
                        EtHargabrg.text.toString().toInt(),
                        EtStokbrg.text.toString().toInt())
                )
                finish()
            }
        }
    }

    private fun tampilbarang(){
        tbbarid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
         suspend {
             val goods = db.TbgoodsDAO().tampilid(tbbarid).get(0)
             val id: String = goods.id_barang.toString()
             val harga: String = goods.harga_barang.toString()
             val stok: String = goods.stok_barang.toString()
             EtIdbrg.setText(id)
             EtNamabrg.setText(goods.nama_barang)
             EtHargabrg.setText(harga)
             EtStokbrg.setText(stok)
         }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}