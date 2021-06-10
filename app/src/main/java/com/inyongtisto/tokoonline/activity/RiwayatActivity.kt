package com.inyongtisto.tokoonline.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterHistory
import com.inyongtisto.tokoonline.adapter.AdapterHistory2
import com.inyongtisto.tokoonline.adapter.AdapterRiwayat
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.helper.SharedPref
import com.inyongtisto.tokoonline.model.ResponModel
import com.inyongtisto.tokoonline.model.Transaction
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.fragment_completed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {

    lateinit var s: SharedPref
    var idCustomer : Int = 0

    var listHistory: ArrayList<Transaction> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        s = SharedPref(this)
//        mainButton()
        getHistoryCompleted()
        getHistoryPogress()
        displayHistory(listHistory)


    }

    fun getHistoryPogress() {
        val user = s.getUser()!!
        idCustomer = user.id
        ApiConfig.instanceRetrofit.getHistoryProgress(idCustomer).enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.Status == "Success") {
                    val arrayHistory = ArrayList<Transaction>()
                    for (p in res.Transactions) {

                        listHistory.add(p)
                    }

//                    listHistory = arrayHistory

                    Log.d("Progress_Add", "size:" + arrayHistory.size)

//                    displayHistory(listHistory)

//                    listHistory = arrayHistory

//                    if (res.Transactions == null){
//                        ckEmpty.visibility = View.VISIBLE
//                    }
//

                }
            }
        })
    }

    fun getHistoryCompleted() {
        val user = s.getUser()!!
        idCustomer = user.id

        ApiConfig.instanceRetrofit.getHistoryCompleted(idCustomer).enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.Status == "Success") {

                    val arrayHistory = ArrayList<Transaction>()
                    for (p in res.Transactions) {
                        listHistory.add(p)
                    }

                    Log.d("Completed_Add", "size:" + res.Transactions.size)

//                    if (res.Transactions == null){
//                        ckEmpty.visibility = View.VISIBLE
//                    }
//
                }
            }
        })
    }


    fun displayHistory(listHistoryOK: ArrayList<Transaction>) {

        Log.d("display_history", "size:" + listHistoryOK.size)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayat.adapter = AdapterRiwayat( this, listHistoryOK )
        rv_riwayat.layoutManager = layoutManager
        Log.d("display_history", "Transactions successfully displayed")
    }

    override fun onResume() {
        getHistoryCompleted()
        getHistoryPogress()
        displayHistory(listHistory)
        super.onResume()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
