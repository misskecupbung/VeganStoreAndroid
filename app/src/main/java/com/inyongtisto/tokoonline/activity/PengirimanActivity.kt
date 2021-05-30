package com.inyongtisto.tokoonline.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterKurir
import com.inyongtisto.tokoonline.app.ApiConfigAlamat
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.model.ModelAlamat
import com.inyongtisto.tokoonline.model.rajaongkir.Costs
import com.inyongtisto.tokoonline.model.rajaongkir.ResponOngkir
import com.inyongtisto.tokoonline.room.MyDatabase
import com.inyongtisto.tokoonline.util.ApiKey
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengirimanActivity : AppCompatActivity() {

    //notification
    //private val CHANNEL_ID ="channel_id_example_01"
    //private val notificationId =101

    lateinit var myDb: MyDatabase
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)
        Helper().setToolbar(this, toolbar, "Pengiriman")
        myDb = MyDatabase.getInstance(this)!!

        //notification
        //createNotificationChannel()
        //btn_bayar.setOnClickListener{
          //  sendNotification()
        //}

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
        mainButton()
        setSepiner()
    }

    //private fun createNotificationChannel(){
      //  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //    val name = "Asik, pesananmu sedang diproses nih!"
          //  val descriptionText = "cek kembali pesananmu dikeranjang agar tidak lupa~"
            //val importance = NotificationManager.IMPORTANCE_DEFAULT
            //val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
              //  description = descriptionText
            //}
            //val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //notificationManager.createNotificationChannel(channel)
        //}
    //}

    //private fun sendNotification(){
      //  val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        //    .setSmallIcon(R.drawable.logo_veganstore)
         //   .setContentTitle("Example Title")
          //  .setContentText("Example Description")
          //  .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //with(NotificationManagerCompat.from(this)){
          //  notify(notificationId, builder.build())
        //}
    //}

    fun setSepiner() {
        val arryString = ArrayList<String>()
        arryString.add("JNE")
        arryString.add("POS")
        arryString.add("TIKI")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arryString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    }

    @SuppressLint("SetTextI18n")
    fun chekAlamat() {

        if (myDb.daoAlamat().getByStatus(true) != null) {
            div_alamat.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE
            div_metodePengiriman.visibility = View.VISIBLE

            val a = myDb.daoAlamat().getByStatus(true)!!
            tv_nama.text = a.name
            tv_phone.text = a.phone
            tv_alamat.text = a.alamat + ", " + a.kota + ", " + a.kecamatan + ", " + a.kodepos + ", (" + a.type + ")"
            btn_tambahAlamat.text = "Ubah Alamat"

            getOngkir("JNE")
        } else {
            div_alamat.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahAlamat.text = "Tambah Alamat"
        }
    }


    private fun mainButton() {
        btn_tambahAlamat.setOnClickListener {
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    private fun getOngkir(kurir: String) {

        val alamat = myDb.daoAlamat().getByStatus(true)

        val origin = "501"
        val destination = "" + alamat!!.id_kota.toString()
        val berat = 1000

        ApiConfigAlamat.instanceRetrofit.ongkir(ApiKey.key, origin, destination, berat, kurir.toLowerCase()).enqueue(object : Callback<ResponOngkir> {
            override fun onResponse(call: Call<ResponOngkir>, response: Response<ResponOngkir>) {
                if (response.isSuccessful) {

                    Log.d("Success", "berhasil memuat data")
                    val result = response.body()!!.rajaongkir.results
                    if (result.isNotEmpty()) {
                        displayOngkir(result[0].code.toUpperCase(), result[0].costs)
                    }


                } else {
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }

            override fun onFailure(call: Call<ResponOngkir>, t: Throwable) {
                Log.d("Error", "gagal memuat data:" + t.message)
            }

        })
    }

    private fun displayOngkir(kurir: String, arrayList: ArrayList<Costs>) {

        var arrayOngkir = ArrayList<Costs>()
        for (i in arrayList.indices) {
            val ongkir = arrayList[i]
            if (i == 0) {
                ongkir.isActive = true
            }
            arrayOngkir.add(ongkir)
        }
        setTotal(arrayOngkir[0].cost[0].value)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        var adapter: AdapterKurir? = null
        adapter = AdapterKurir(arrayOngkir, kurir, object : AdapterKurir.Listeners {
            override fun onClicked(data: Costs, index: Int) {
                val newArrayOngkir = ArrayList<Costs>()
                for (ongkir in arrayOngkir) {
                    ongkir.isActive = data.description == ongkir.description
                    newArrayOngkir.add(ongkir)
                }
                arrayOngkir = newArrayOngkir
                adapter!!.notifyDataSetChanged()
                setTotal(data.cost[0].value)
            }
        })
        rv_metode.adapter = adapter
        rv_metode.layoutManager = layoutManager
    }

    fun setTotal(ongkir: String) {
        tv_ongkir.text = Helper().gantiRupiah(ongkir)
        tv_total.text = Helper().gantiRupiah(Integer.valueOf(ongkir) + totalHarga)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        chekAlamat()
        super.onResume()
    }
}
