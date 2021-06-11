package com.inyongtisto.tokoonline.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import com.inyongtisto.tokoonline.MainActivity
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.Transaction
import com.inyongtisto.tokoonline.model.order_detail
import com.inyongtisto.tokoonline.room.MyDatabase
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.toolbar_transaction.*

class SuccessActivity : AppCompatActivity() {

    var berlangganan = ""
    var pakePlastice = ""
    var idTransaktion = 0

    //notification
//    private val CHANNEL_ID ="channel_id_example_01"
//    private val notificationId =101


    lateinit var  transaction : Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        mainButton()
        getDataTransaction()
        diplayAttribute()
    }





    private fun getDataTransaction() {
        val data = intent.getStringExtra("transaksi")
        transaction = Gson().fromJson<Transaction>(data, Transaction::class.java)
    }

    private fun diplayAttribute() {
        tv_invoice_sc.text = transaction.invoice
        tv_time_sc.text = transaction.created_at
        displaystatus()
        tv_customer_name_sc.text = transaction.customer_name
        tv_customer_phone_sc.text = transaction.customer_phone
        tv_address_sc.text = transaction.address
        tv_detailSector_sc.text = transaction.detailSector_name
        tv_delivery_date_sc.text = transaction.delivery_date
        tv_delivery_time_sc.text = transaction.delivery_time
        displayuseplastic()
        displayberlangganan()
        tv_note_sc.text = transaction.note
        tv_subtotal_sc.text = Helper().gantiRupiah(transaction.subtotal)
        displayongkir()
        tv_total_dt_sc.text = Helper().gantiRupiah(transaction.total)
        tv_item_sc.text = Helper().gantiRupiah(transaction.total_item)
    }

    private fun displayongkir() {
        if (transaction.delivery_price == 0){
            tv_delivery_price_sc.text = "Rp. 0"
        }else {
            tv_delivery_price_sc.text = Helper().gantiRupiah(transaction.delivery_price)
        }
    }

    private fun displayuseplastic() {
        if (transaction.useplastic == 1){
            tv_useplastic_sc.text = getString(R.string.ly_detailProduk_useplastic_iya)
        }else {
            tv_useplastic_sc.text = getString(R.string.ly_detailProduk_useplastic_tidak)
        }
    }

    private fun displayberlangganan()  {
        if (transaction.scheduled == 1){
            tv_scheduled_sc.text = getString(R.string.ly_detailProduk_berlangganan_iya)
        }else {
            tv_scheduled_sc.text = getString(R.string.ly_detailProduk_berlangganan_tidak)
        }
    }

    private fun displaystatus() {
        if (transaction.status == "Success"){
            tv_status_sc.text = getString(R.string.ly_detailProduk_status_sukses)
        }else {
            tv_status_sc.text = getString(R.string.ly_detailProduk_status_progres)
        }
    }

    private fun mainButton() {
        close.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
