package com.inyongtisto.tokoonline.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.activity.DetailHistoryActivity
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.model.Transaction

class AdapterHistory2 (var activity: Activity, var data: ArrayList<Transaction>)
    : RecyclerView.Adapter<AdapterHistory2.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama_history)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga_history)
        val tvTangal = view.findViewById<TextView>(R.id.tv_tgl_history)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah_history)
        val tvInvoice = view.findViewById<TextView>(R.id.item_invoice)
        //        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val layout = view.findViewById<CardView>(R.id.layout_history)
    }

    //    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.customer_name
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(a.total)
        holder.tvJumlah.text = a.total_item.toString() + " Items"
//        holder.tvStatus.text = a.status
        holder.tvTangal.text = a.created_at
        holder.tvInvoice.text = a.invoice

//        var color = context.getColor(R.color.menungu)
//        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
//        else if (a.status == "BATAL") color = context.getColor(R.color.batal)
//
//        holder.tvStatus.setTextColor(color)

        Log.d("adapterHistory", "Transactions successfully displayed")

        holder.layout.setOnClickListener {

            TransactionDetail(data[position])

//            listener.onClicked(a)
        }
    }

    private fun TransactionDetail(dt_select: Transaction) {
        val dataTransaction = dt_select


        val activiti = Intent(activity, DetailHistoryActivity::class.java)
        val str = Gson().toJson(dataTransaction, Produk::class.java)
        activiti.putExtra("extra", str)

        activity.startActivity(activiti)
    }

}