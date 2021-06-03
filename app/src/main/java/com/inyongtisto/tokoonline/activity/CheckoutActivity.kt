package com.inyongtisto.tokoonline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.inyongtisto.tokoonline.MainActivity
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.room.MyDatabase
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_pengiriman.*


class CheckoutActivity : AppCompatActivity() {
    lateinit var myDb: MyDatabase
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)
        myDb = MyDatabase.getInstance(this)!!

        btn_bayar.setOnClickListener{
            Alerter.create(this)
            Alerter.create(this)
                .setTitle("Yay! Pesananmu sedang kami proses :)")
                .setText("jangan lupa untuk check kembali ya!")
                .setBackgroundColorRes(R.color.colorPrimaryVegan)
                .setDuration(7000)
                .setOnClickListener(View.OnClickListener {
                    Toast.makeText(applicationContext, "Notification clicked", Toast.LENGTH_SHORT).show()

                })

                .show()
        }

    }






}