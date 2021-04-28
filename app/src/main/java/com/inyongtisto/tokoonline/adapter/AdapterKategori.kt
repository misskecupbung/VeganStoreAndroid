package com.inyongtisto.tokoonline.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.activity.DetailProdukActivity
import com.inyongtisto.tokoonline.activity.ProductActivity
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.Kategori
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.util.Config
import com.squareup.picasso.Picasso


class AdapterKategori(var activity: Activity, var data: ArrayList<Kategori>) :
    RecyclerView.Adapter<AdapterKategori.Holder>()  {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama_kategori)
        val imgKategori = view.findViewById<ImageView>(R.id.img_kategori)
        val layout = view.findViewById<LinearLayout>(R.id.layout_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterKategori.Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return AdapterKategori.Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {


//        val hargaAsli = Integer.valueOf(ctg.harga)
//        var harga = Integer.valueOf(ctg.harga)
//
//        if (a.discount != 0){
//            harga -= a.discount
//        }
//
//        holder.tvHargaAsli.text = Helper().gantiRupiah(hargaAsli)
//        holder.tvHargaAsli.paintFlags = holder.tvHargaAsli.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.tvNama.text = data[position].name
//        holder.tvHarga.text = Helper().gantiRupiah(harga)
//        holder.imgProduk.setImageResource(data[position].image)

        val image = Config.categoryUrl + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.imgKategori)

        holder.layout.setOnClickListener {
            val productactivity = Intent(activity, ProductActivity::class.java)
            val category = data[position].name
            productactivity.putExtra("extra", category)
            productactivity.putExtra("search", "")
            activity.startActivity(productactivity)
        }
    }
}