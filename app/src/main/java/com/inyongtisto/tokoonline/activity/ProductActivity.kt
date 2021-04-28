package com.inyongtisto.tokoonline.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterKategori
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.fragment.HomeFragment
import com.inyongtisto.tokoonline.fragment.ProductFragment
import com.inyongtisto.tokoonline.model.Kategori
import com.inyongtisto.tokoonline.model.ResponModel
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {

    private val fragmentProduct: Fragment = ProductFragment()
    private val fm: FragmentManager = supportFragmentManager

    var categoryChoose: String = ""
    var searchProduct: String = ""

    private var listKategori: ArrayList<Kategori> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        getInfoCategory()
        getInfoSearch()
        checkInfo()
        getKategori()
        sendDataFragment()

    }

    @SuppressLint("LongLogTag")
    private fun getInfoSearch() {
        var search = intent.getStringExtra("search")
        if (search != "") {
            searchProduct = search
            categoryChoose = ""
            Log.d("getInfo()-Product_Activity", "Get Info Search Query")
        }
    }

    @SuppressLint("LongLogTag")
    private fun sendDataFragment() {

        Log.d("sendDataFragment()-Product_Activity", "Start")
        val bundle = Bundle().apply {
            putString("CategoryChoose", categoryChoose)
            putString("SearchProduct", searchProduct)
        }


        fragmentProduct.arguments = bundle
        Log.d("sendDataFragment()-Product_Activity", "Bundle")

        fm.beginTransaction().replace(R.id.fragment_product, fragmentProduct)
            .show(fragmentProduct)
            .commit()

        Log.d("sendDataFragment()-Product_Activity", "Completed")
    }

    @SuppressLint("LongLogTag")
    private fun getInfoCategory() {

        Log.d("getInfo()-Product_Activity", "Start")
        var category = intent.getStringExtra("extra")
        Log.d("getInfo()-Product_Activity", "Get Info (Category or search query)")

        if (category != "") {
            categoryChoose = category
            searchProduct = ""
            Log.d("getInfo()-Product_Activity", "Get Info Category")
        }

        Log.d("getInfo()-Product_Activity", "Completed")
    }

    @SuppressLint("LongLogTag")
    private fun checkInfo() {

        Log.d("checkInfo()-Product_Activity", "Start")
        if (categoryChoose != ""){
            searchProduct = ""
        }
        else{
            categoryChoose = ""
        }
        Log.d("checkInfo()-Product_Activity", "Completed")

    }

    @SuppressLint("LongLogTag")
    private fun getKategori() {
        Log.d("getKategori()-Product_Activity", "Start")
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
        Log.d("getKategori()-Product_Activity", "Completed")
    }

    fun displayKategori(){
        Log.d("display_categories", "size" + listKategori.size)

        val layoutManagerKategori = LinearLayoutManager(this)
        layoutManagerKategori.orientation = LinearLayoutManager.HORIZONTAL

        rv_category.layoutManager = layoutManagerKategori
        rv_category.adapter = AdapterKategori(this,listKategori)


        Log.d("display_categories", "Categories successfully displayed")
    }
}
