package com.inyongtisto.tokoonline.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.room.MyDatabase
import com.inyongtisto.tokoonline.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AdapterKeranjangCK(var activity: Activity, var data: ArrayList<Produk>, var listener: Listeners) : RecyclerView.Adapter<AdapterKeranjangCK.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama_keranjang_ck)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga_keranjang_ck)
        val tvPrice = view.findViewById<TextView>(R.id.tv_harga_keranjang_ck)
        val tvTypeunit = view.findViewById<TextView>(R.id.tv_typeunit_keranjang_ck)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk_keranjang_ck)
        val layout = view.findViewById<CardView>(R.id.layout_keranjang_ck)

        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah_keranjang_ck)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang_keranjang_ck)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete_keranjang_ck)

//        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah_keranjang_ck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang_ck, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val produk = data[position]
        val harga = Integer.valueOf(produk.price)

        holder.tvNama.text = produk.name
        holder.tvTypeunit.text = produk.typeunit
        holder.tvPrice.text = Helper().gantiRupiah(harga)
        holder.tvHarga.text = Helper().gantiRupiah(harga * produk.jumlah)

        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

//        holder.checkBox.isChecked = produk.selected
//        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//            produk.selected = isChecked
//            update(produk)
//        }

        val image = Config.productUrl + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.vegetables_cart)
            .error(R.drawable.vegetables_cart)
            .into(holder.imgProduk)


        holder.btnTambah.setOnClickListener {
            jumlah++
            produk.jumlah = jumlah
            update(produk)

            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btnKurang.setOnClickListener {
            if (jumlah <= 1) return@setOnClickListener
            jumlah--

            produk.jumlah = jumlah
            update(produk)

            holder.tvJumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga * jumlah)
        }

        holder.btnDelete.setOnClickListener {
            delete(produk)
            listener.onDelete(position)
        }
    }

    interface Listeners {
        fun onUpdate()
        fun onDelete(position: Int)
    }

    private fun update(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    private fun delete(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            })
    }

}