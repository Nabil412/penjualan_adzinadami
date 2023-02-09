package com.example.penjualan_adzinadami


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.penjualan_adzinadami.room.tb_Barang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*

class BarangAdapter (private val barang:ArrayList<tb_Barang>, private val listener:onAdapterListener )
    :RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){
        class BarangViewHolder(val view: View): RecyclerView.ViewHolder(view){

        }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
       return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent,false)
       )
     }

     override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
         val brg = barang[position]
         holder.view.Tnama1.text = brg.nama_barang
         holder.view.Tnama2.text = brg.harga_barang.toString()
         holder.view.Tview.setOnClickListener{ listener.onClick(brg)}
         holder.view.imgEdit.setOnClickListener{listener.onUpdate(brg)}
         holder.view.imgDelete.setOnClickListener{listener.onDelete(brg)}
     }

    override fun getItemCount() = barang.size

    fun setData(list: List<tb_Barang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }

    interface  onAdapterListener{
        fun onClick(tbbar: tb_Barang)
        fun onUpdate(tbbar: tb_Barang)
        fun onDelete(tbbar: tb_Barang)
    }

    }

