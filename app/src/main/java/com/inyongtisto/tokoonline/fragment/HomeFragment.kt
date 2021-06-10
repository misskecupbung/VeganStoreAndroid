package com.inyongtisto.tokoonline.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.activity.ProductActivity
import com.inyongtisto.tokoonline.adapter.AdapterKategori
import com.inyongtisto.tokoonline.adapter.AdapterProduk
import com.inyongtisto.tokoonline.adapter.AdapterSlider
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.model.Kategori
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    lateinit var rvProduk: RecyclerView
    lateinit var rvKategori: RecyclerView
    lateinit var scProduct: SearchView

//    lateinit var vpSlider: ViewPager
//    lateinit var rvProdukTerlasir: RecyclerView
//    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getKategori()
        getProduk()
        searchProduct(scProduct)
        return view
    }

    private fun searchProduct(scProduct: SearchView) {
        scProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val productactivity = Intent(activity, ProductActivity::class.java)
                val search = query
                productactivity.putExtra("search", search)
                productactivity.putExtra("extra", "")
                activity?.startActivity(productactivity)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    fun displayKategori(){
        Log.d("display_categories", "size" + listKategori.size)

        val layoutManagerKategori = LinearLayoutManager(activity)
        layoutManagerKategori.orientation = LinearLayoutManager.HORIZONTAL

        rvKategori.adapter = AdapterKategori(requireActivity(), listKategori)
        rvKategori.layoutManager = layoutManagerKategori

        Log.d("display_categories", "Categories successfully displayed")

    }

    fun displayProduk() {
        Log.d("display_products", "size:" + listProduk.size)

//        val arrSlider = ArrayList<Int>()
//        arrSlider.add(R.drawable.slider1)
//        arrSlider.add(R.drawable.slider2)
//        arrSlider.add(R.drawable.slider3)

//        val adapterSlider = AdapterSlider(arrSlider, activity)
//        vpSlider.adapter = adapterSlider

        val layoutManagerProduct = LinearLayoutManager(activity)
        layoutManagerProduct.orientation = LinearLayoutManager.VERTICAL

//        val layoutManager2 = LinearLayoutManager(activity)
//        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

//        val layoutManager3 = LinearLayoutManager(activity)
//        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManagerProduct

//        rvProdukTerlasir.adapter = AdapterProduk(requireActivity(), listProduk)
//        rvProdukTerlasir.layoutManager = layoutManager2

//        rvElektronik.adapter = AdapterProduk(requireActivity(), listProduk)
//        rvElektronik.layoutManager = layoutManager3
        Log.d("display_products", "Products successfully displayed")
    }

    private var listKategori: ArrayList<Kategori> = ArrayList()

    fun getKategori() {
        ApiConfig.instanceRetrofit.getKategori().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.Status == "Success") {
                    val arrayKategori = ArrayList<Kategori>()
                    for (p in res.Categories) {
                        arrayKategori.add(p)
                    }
                    listKategori = arrayKategori
                    displayKategori()

                    Log.d("get_categories", res.Message)
                }
            }

        })
    }

    private var listProduk: ArrayList<Produk> = ArrayList()

    fun getProduk() {
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.Status == "Success") {
                    val arrayProduk = ArrayList<Produk>()
                    for (p in res.Products) {
                        arrayProduk.add(p)
                    }
                    listProduk = arrayProduk
                    displayProduk()
                }
            }
        })
    }

    fun init(view: View) {
        //vpSlider = view.findViewById(R.id.vp_slider)
        rvKategori = view.findViewById(R.id.rv_category)
        rvProduk = view.findViewById(R.id.rv_produk)
        scProduct = view.findViewById(R.id.search_product_home)
        //rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
        //rvElektronik = view.findViewById(R.id.rv_elektronik)
    }

    override fun onResume() {
        getProduk()
        getKategori()
        super.onResume()
    }

//    val arrProduk: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.hp_14_bs749tu
//
//        val p2 = Produk()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.hp_envy_13_aq0019tx
//
//        val p3 = Produk()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.hp_pavilion_13_an0006na
//
//        val p4 = Produk()
//        p4.name = "Hp Envy_13_aq0019tx"
//        p4.harga = "Rp.15.980.000"
//        p4.gambar = R.drawable.hp_pavilion_14_ce1507sa
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//
//        return arr
//    }

}
