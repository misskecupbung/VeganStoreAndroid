package com.inyongtisto.tokoonline.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterProduk
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

    lateinit var rvProduk: RecyclerView
    var categoryChoose: String = ""
    var searchProduct: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)
        init(view)

        categoryChoose = arguments?.getString("CategoryChoose").toString()

        searchProduct = arguments?.getString("SearchProduct").toString()

        productAction()

        return view
    }

    private fun productAction() {
        if (categoryChoose != "") {
            getCategoryChoose(categoryChoose)
            Log.d("ProductAction", "categoryChoose =" + categoryChoose)
        }
        if (searchProduct != "") {
            getSearchProduk(searchProduct)
            Log.d("ProductAction", "searchProduct =" + searchProduct)
        }
    }

    fun init(view: View) {
        rvProduk = view.findViewById(R.id.rv_produk)
    }

    private var listProduk: ArrayList<Produk> = ArrayList()

    fun getCategoryChoose(category: String) {
        ApiConfig.instanceRetrofit.getCategoryChoose(category).enqueue(object : Callback<ResponModel> {
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
                Log.d("getCategoryChoose", "getCategoryChoose")
            }
        })
    }

    fun getSearchProduk(keyword: String) {
        ApiConfig.instanceRetrofit.getSearchProduk(keyword).enqueue(object : Callback<ResponModel> {
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
                Log.d("getSearchProduk", "getSearchProduk")
            }
        })
    }

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

    private fun displayProduk() {
        Log.d("display_products", "size:" + listProduk.size)

        val layoutManagerProduct = LinearLayoutManager(activity)
        layoutManagerProduct.orientation = LinearLayoutManager.VERTICAL

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManagerProduct

        Log.d("display_products", "Products successfully displayed")
    }
}




