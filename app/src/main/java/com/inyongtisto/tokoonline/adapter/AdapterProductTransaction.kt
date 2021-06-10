package com.inyongtisto.tokoonline.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.activity.DetailHistoryActivity
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.order_detail

class AdapterProductTransaction(var activity: Activity, var data: ArrayList<order_detail>)
    : RecyclerView.Adapter<AdapterProductTransaction.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_product_name_trans)
        val tvQty = view.findViewById<TextView>(R.id.tv_qty)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga_trans)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_total_price_item)
        val layout = view.findViewById<LinearLayout>(R.id.layout_detail_transaction)
    }

    //    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        holder.tvNama.text = a.product_name
        holder.tvQty.text = a.qty.toString()
        holder.tvHarga.text = Helper().gantiRupiah(a.price)
        holder.tvJumlah.text = Helper().gantiRupiah(a.qty * a.price)

//        val name = a.customer_name
//        holder.tvNama.text = name
//        holder.tvHarga.text = Helper().gantiRupiah(a.total)
//        holder.tvJumlah.text = a.total_item.toString() + " Items"
////        holder.tvStatus.text = a.status
//        holder.tvTangal.text = a.created_at
//        holder.tvInvoice.text = a.invoice
//
//        if (a.status == "Success"){
//            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"))
//            holder.tvStatus.text = "Sudah Selesai"
//        }
//        else {
//            holder.tvStatus.setTextColor(Color.parseColor("#FF00BCD4"))
//            holder.tvStatus.text = "Sedang Diproses"
//        }

//        var color = context.getColor(R.color.menungu)
//        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
//        else if (a.status == "BATAL") color = context.getColor(R.color.batal)
//
//        holder.tvStatus.setTextColor(color)

        Log.d("adapterHistory", "Transactions successfully displayed")

//
    }

//    private fun TransactionDetail(dt_select: Int) {
//        val idTransaction = dt_select
//
//
//        val activityDetail = Intent(activity, DetailHistoryActivity::class.java)
//        val id = idTransaction
//        activityDetail.putExtra("extra", id)
//
//        activity.startActivity(activityDetail)
//    }

}