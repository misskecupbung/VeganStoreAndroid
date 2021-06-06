package com.inyongtisto.tokoonline.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.inyongtisto.tokoonline.MainActivity
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.helper.SharedPref
import com.inyongtisto.tokoonline.room.MyDatabase
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_pengiriman.*


class CheckoutActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        myDb = MyDatabase.getInstance(this)!!

        btn_bayar.setOnClickListener {
            Alerter.create(this)
            Alerter.create(this)
                .setTitle("Yay! Pesananmu sedang kami proses :)")
                .setText("jangan lupa untuk check kembali ya!")
                .setBackgroundColorRes(R.color.colorPrimaryVegan)
                .setDuration(7000)
                .setOnClickListener(View.OnClickListener {
                    Toast.makeText(applicationContext, "Notification clicked", Toast.LENGTH_SHORT)
                        .show()

                })
                .show()
            //bayar()
        }
        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
        setSpinnerSektor()
        setSpinnerWaktuKirim()

    }

    //spinner sektor kota
    fun setSpinnerSektor() {
        val arryString = ArrayList<String>()
        arryString.add("Tegal")
        arryString.add("Garut")
        arryString.add("Purwokerto")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arryString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_kota.adapter = adapter
        spinner_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //if (position != 0) {
                //  getOngkir(spinner_kota.selectedItem.toString())
            }
        }
    }

    //spinner waktu kirim
    fun setSpinnerWaktuKirim() {
        val arryString = ArrayList<String>()
        arryString.add("Pagi Hari (09.00 WIB - 11.00 WIB)")
        arryString.add("Siang Hari (13.00 WIB - 15.00 WIB)")
        arryString.add("Sore Hari (16.00 WIB - 17.00 WIB)")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arryString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_waktu_kirim.adapter = adapter
        spinner_waktu_kirim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //if (position != 0) {
                //  getOngkir(spinner_kota.selectedItem.toString())
            }
        }
    }
}






