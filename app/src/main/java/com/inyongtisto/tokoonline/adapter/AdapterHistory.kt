package com.inyongtisto.tokoonline.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
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
import com.inyongtisto.tokoonline.activity.DetailTransaktion
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.model.Transaction

class AdapterHistory( var activity: Activity, var data: ArrayList<Transaction>)
    : RecyclerView.Adapter<AdapterHistory.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama_history)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga_history)
        val tvTangal = view.findViewById<TextView>(R.id.tv_tgl_history)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah_history)
        val tvInvoice = view.findViewById<TextView>(R.id.item_invoice)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status_history)
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

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.customer_name
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(a.total)
        holder.tvJumlah.text = a.total_item.toString() + " Items"
//        holder.tvStatus.text = a.status
        holder.tvTangal.text = a.created_at
        holder.tvInvoice.text = a.invoice

        if (a.status == "Success"){
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"))
            holder.tvStatus.text = "Sudah Selesai"
        }
        else {
            holder.tvStatus.setTextColor(Color.parseColor("#FF00BCD4"))
            holder.tvStatus.text = "Sedang Diproses"
        }

//        var color = context.getColor(R.color.menungu)
//        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
//        else if (a.status == "BATAL") color = context.getColor(R.color.batal)
//
//        holder.tvStatus.setTextColor(color)

        Log.d("adapterHistory", "Transactions successfully displayed")

        holder.layout.setOnClickListener {

//            TransactionDetail(data[position].id)
            val data = Gson().toJson(data[position], Transaction::class.java)

            Log.d("adapterHistory_selected","Data = " + data )

            val activityDetail = Intent(activity, DetailTransaktion::class.java)
            activityDetail.putExtra("idSelected", data)

            activity.startActivity(activityDetail)

//            listener.onClicked(a)
        }
    }

//    private fun TransactionDetail(dt_select: Int) {
//        val idTransaction = dt_select
//        val data = Gson().toJson(data[position], Produk::class.java)
//
//        Log.d("adapterHistory_selected","Id" + idTransaction)
//
//        val activityDetail = Intent(activity, DetailTransaktion::class.java)
//        activityDetail.putExtra("idSelected", idTransaction)
//
//        activity.startActivity(activityDetail)
//    }

}