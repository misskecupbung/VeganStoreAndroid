package com.inyongtisto.tokoonline.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterHistory
import com.inyongtisto.tokoonline.adapter.AdapterHistory2
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.helper.SharedPref
import com.inyongtisto.tokoonline.model.ResponModel
import com.inyongtisto.tokoonline.model.Transaction
import kotlinx.android.synthetic.main.fragment_completed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompletedFragment : Fragment() {

    lateinit var rvCompleted: RecyclerView
    lateinit var ckEmpty: LinearLayout
    lateinit var s: SharedPref
    var idCustomer : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_completed, container, false)
        init(view)

        s = SharedPref(requireActivity() as Activity)
        mainButton()

        val user = s.getUser()!!
        idCustomer = user.id
        getHistoryCompleted(idCustomer)

        return view
    }

    private fun mainButton() {
//        btn_belanja.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//        }
    }


    fun init(view: View) {
        rvCompleted = view.findViewById(R.id.rv_completed)
        ckEmpty = view.findViewById(R.id.empty)
    }

    private var listHistoryCompleted: ArrayList<Transaction> = ArrayList()

    fun getHistoryCompleted(id_customer : Int) {
        ApiConfig.instanceRetrofit.getHistoryCompleted(id_customer).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.Status == "Success") {

                    val arrayHistory = ArrayList<Transaction>()
                    for (p in res.Transactions) {
                        arrayHistory.add(p)
                    }
                    listHistoryCompleted = arrayHistory

//                    if (res.Transactions == null){
//                        ckEmpty.visibility = View.VISIBLE
//                    }
//
                    displayCompleted()
                }
                Log.d("display_completed", "size:" + listHistoryCompleted.size)

            }
        })

    }

    fun displayCompleted() {

        Log.d("display_completed", "size:" + listHistoryCompleted.size)

        val layoutManager = LinearLayoutManager(this.requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_completed.adapter = AdapterHistory( requireActivity(), listHistoryCompleted )
        rv_completed.layoutManager = layoutManager
        Log.d("display_completed", "Transactions successfully displayed")
    }


//    override fun onResume() {
//        getHistoryCompleted(idCustomer)
//        super.onResume()
//    }
}
