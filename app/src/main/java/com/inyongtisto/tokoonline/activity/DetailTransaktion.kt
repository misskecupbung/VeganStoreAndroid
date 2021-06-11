package com.inyongtisto.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.inyongtisto.tokoonline.MainActivity
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterHistory
import com.inyongtisto.tokoonline.adapter.AdapterProductTransaction
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.*
import kotlinx.android.synthetic.main.activity_detail_transaktion.*
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.fragment_completed.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_transaction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaktion : AppCompatActivity() {

    var berlangganan = ""
    var pakePlastice = ""
    var idTransaktion = 0
    var listProdukTransaction : ArrayList<order_detail> = ArrayList()
    lateinit var  transaction : Transaction
    lateinit var  transactionBfr : Transaction


    override fun onCreate(savedInstanceState: Bundle?) {
//        Helper().setToolbar(this, toolbar_transaction, "Resi")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaktion)
        mainButton()
        getDetailTransaction()
    }

    private fun getDetailTransaction() {
        val data = intent.getStringExtra("idSelected")
        transactionBfr = Gson().fromJson<Transaction>(data, Transaction::class.java)
        Log.d("detailTransaktion", "Id = " + transactionBfr)
        idTransaktion = transactionBfr.id

        ApiConfig.instanceRetrofit.getDetailHistory(idTransaktion).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                transaction = res.Transaction

                if (res.Status == "Success") {

                    val arrayProductTransaction = ArrayList<order_detail>()
                    for (p in res.Transaction.order_detail) {
                        arrayProductTransaction.add(p)
                    }
                    listProdukTransaction = arrayProductTransaction

//                    if (res.Transactions == null){
//                        ckEmpty.visibility = View.VISIBLE
//                    }
//
                    displayDetailTransaction()

                    diplayAttribute()
                }
                Log.d("display_transaction", "size:" + listProdukTransaction.size)

            }
        })
    }

    private fun displayDetailTransaction() {
        Log.d("display_completed", "size:" + listProdukTransaction.size)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_cart.adapter = AdapterProductTransaction( this, listProdukTransaction )
        rv_cart.layoutManager = layoutManager
        Log.d("display_completed", "Transactions successfully displayed")
    }

    private fun diplayAttribute() {
        tv_invoice.text = transaction.invoice
        tv_time.text = transaction.created_at
        displaystatus()
        tv_customer_name.text = transaction.customer_name
        tv_customer_phone.text = transaction.customer_phone
        tv_address.text = transaction.address
        tv_detailSector.text = transaction.detailSector_name
        tv_delivery_date.text = transaction.delivery_date
        tv_delivery_time.text = transaction.delivery_time
        displayuseplastic()
        displayberlangganan()
        tv_note.text = transaction.note
        tv_subtotal.text = Helper().gantiRupiah(transaction.subtotal)
        displayongkir()
        tv_total_dt.text = Helper().gantiRupiah(transaction.total)
        tv_item.text = Helper().gantiRupiah(transaction.total_item)
    }

    private fun displayongkir() {
        if (transaction.delivery_price == 0){
            tv_delivery_price.text = "Rp. 0"
        }else {
            tv_delivery_price.text = Helper().gantiRupiah(transaction.delivery_price)
        }
    }

    private fun displayuseplastic() {
        if (transaction.useplastic == 1){
            tv_useplastic.text = getString(R.string.ly_detailProduk_useplastic_iya)
        }else {
            tv_useplastic.text = getString(R.string.ly_detailProduk_useplastic_tidak)
        }
    }

    private fun displayberlangganan()  {
        if (transaction.scheduled == 1){
            tv_scheduled.text = getString(R.string.ly_detailProduk_berlangganan_iya)
        }else {
            tv_scheduled.text = getString(R.string.ly_detailProduk_berlangganan_tidak)
        }
    }

    private fun displaystatus() {
        if (transaction.status == "Success"){
            tv_status.text = getString(R.string.ly_detailProduk_status_sukses)
        }else {
            tv_status.text = getString(R.string.ly_detailProduk_status_progres)
        }
    }

    private fun mainButton() {
        close.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
