package com.inyongtisto.tokoonline.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.inyongtisto.tokoonline.MainActivity

import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.activity.LoginActivity
import com.inyongtisto.tokoonline.activity.ProductActivity
import com.inyongtisto.tokoonline.activity.RegisterActivity
import com.inyongtisto.tokoonline.helper.SharedPref

/**
 * A simple [Fragment] subclass.
 */
class AkunFragment : Fragment() {

    lateinit var s: SharedPref
    lateinit var btnLogout: TextView
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = SharedPref(requireActivity() as Activity)

        btnLogout.setOnClickListener {
            s.setStatusLogin(false)
            login()
        }

        setData()
        return view
    }

    private fun login() {
        val loginactivity = Intent(activity, LoginActivity::class.java)
        activity?.startActivity(loginactivity)
    }


    fun setData() {

        if (s.getUser() == null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }

        val user = s.getUser()!!

        tvNama.text = user.name
        tvEmail.text = user.email
        tvPhone.text = user.phone
    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvEmail = view.findViewById(R.id.tv_email)
        tvPhone = view.findViewById(R.id.tv_phone)
    }


}
