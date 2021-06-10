package com.inyongtisto.tokoonline.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.gson.Gson
import com.inyongtisto.tokoonline.R
import com.inyongtisto.tokoonline.adapter.AdapterKeranjang
import com.inyongtisto.tokoonline.adapter.AdapterKeranjangCK
import com.inyongtisto.tokoonline.adapter.AdapterKurir
import com.inyongtisto.tokoonline.app.ApiConfig
import com.inyongtisto.tokoonline.app.ApiConfigAlamat
import com.inyongtisto.tokoonline.helper.Helper
import com.inyongtisto.tokoonline.helper.SharedPref
import com.inyongtisto.tokoonline.model.*
import com.inyongtisto.tokoonline.model.rajaongkir.Costs
import com.inyongtisto.tokoonline.model.rajaongkir.ResponOngkir
import com.inyongtisto.tokoonline.room.MyDatabase
import com.inyongtisto.tokoonline.util.ApiKey
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.activity_pengiriman.pb_pengiriman
import kotlinx.android.synthetic.main.activity_pengiriman.rv_produk
import kotlinx.android.synthetic.main.activity_pengiriman.tv_nama
import kotlinx.android.synthetic.main.activity_pengiriman.tv_phone
import kotlinx.android.synthetic.main.activity_pengiriman.tv_total
import kotlinx.android.synthetic.main.item_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PengirimanActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase

    //notification
    private val CHANNEL_ID ="channel_id_example_01"
    private val notificationId =101

    var totalHarga = 0
    var sector_Id = 0
    var sectorDetail_Name = ""
    var ongkir = 10000
    var selected = 1
    var unselected = 0
    var langgan_status = 0
    var kemasan_status = 0
    var getWaktu = ""
    var paymethode ="CASH"
    lateinit var s: SharedPref
    lateinit var calendar: Calendar
    var tanggal_kirim = ""
    lateinit var checkoutSucces : Chekout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengiriman)

        s = SharedPref(this)

        Helper().setToolbar(this, toolbar, "Pengiriman")
        myDb = MyDatabase.getInstance(this)!!

        createNotificationChannel()

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)

        mainButton()
        getUserProfil()
        getSectroDetail()
//        displayProduk()
        waktuSpinner()
    }

    @SuppressLint("LongLogTag")
    private fun getUserProfil() {
        val user = SharedPref(this).getUser()!!

        edt_nama.setText(user.name)
        edt_phone.setText(user.phone)
        Log.d("getUserProfil()-Pengiriman_Activity", "set edt_nama dan edt_phone")
    }


    @SuppressLint("LongLogTag")
    private fun waktuSpinner() {
        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Waktu Pengiriman*")
        arrayString.add("Shubuh (04.00-06.00)")
        arrayString.add("Pagi (07.00-09.00)")
        arrayString.add("Siang (12.00-14.00)")
        arrayString.add("Sore (15.00-17.00)")
        arrayString.add("Malam (19.00-21.00)")

        val adapter = ArrayAdapter<Any>(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_waktu.adapter = adapter
        spinner_waktu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    getWaktu = spinner_waktu.selectedItem.toString()
                }
            }
        }
        Log.d("waktuSpinner()-Pengiriman_Activity", "done, can display waktu dropdown")
    }

    @SuppressLint("LongLogTag")
//    Maksudnya getSectorDetail()
    private fun getSectroDetail() {
        ApiConfig.instanceRetrofit.getSectroDetail().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                if (response.isSuccessful) {

                    val res = response.body()!!
                    val arrayString = ArrayList<String>()
                    val arraySectorDetail = ArrayList<ModelSectorDetail>()

                    if (res.Status == "Success") {

                        arrayString.add("Pilih Sector*")

                        for (sd in res.SectorDetails) {
                            arraySectorDetail.add(sd)
                            arrayString.add(sd.name)
                        }

                        Log.d("get_sectorDetails", res.Message)
                    }


                    val adapter = ArrayAdapter<Any>(this@PengirimanActivity, R.layout.item_spinner, arrayString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner_sector.adapter = adapter
                    spinner_sector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            if (position != 0) {
                                var sectorDetail = arraySectorDetail[position - 1]
                                sector_Id = sectorDetail.sector_id.toInt()
                                sectorDetail_Name = sectorDetail.name

                            }
                        }
                    }
                    Log.d("getSectorDetails()-Pengiriman_Activity", "sector id = " + sector_Id + "" + "Name Sector Detail = " + sectorDetail_Name )

                } else {
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }
        })
        Log.d("getSectorDetails()-Pengiriman_Activity", "done, can display area dropdown")
    }

//    private fun ProdukDisplay(){}

    lateinit var adapter: AdapterKeranjangCK
    var listProduk = ArrayList<Produk>()
    var listProdukSelected = ArrayList<Produk>()

    @SuppressLint("LongLogTag")
    private fun displayProduk() {
        listProduk = myDb.daoKeranjang().getAll() as ArrayList
        for (p in listProduk) {
            if (p.selected) {
                listProdukSelected.add(p)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjangCK(this, listProdukSelected, object : AdapterKeranjangCK.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }
        })
        rv_produk.adapter = adapter
        rv_produk.layoutManager = layoutManager
        Log.d("displayProduk()-Pengiriman_Activity", "done, can display produk from keranjang")
    }

    @SuppressLint("LongLogTag")
    fun hitungTotal() {
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (produk in listProduk) {
            if (produk.selected) {
                val harga = Integer.valueOf(produk.price)
                totalHarga += (harga * produk.jumlah)
            } else {
                isSelectedAll = false
            }
        }
        Log.d("hitungTotal()-Pengiriman_Activity", "done, totalHarga = " + totalHarga)
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
        tv_ongkir.text = Helper().gantiRupiah(ongkir)
        tv_total.text = Helper().gantiRupiah(ongkir + totalHarga)
    }

    @SuppressLint("LongLogTag")
    private fun mainButton() {
//        btn_tambahAlamat.setOnClickListener {
//            startActivity(Intent(this, ListAlamatActivity::class.java))
//        }

        btn_bayar.setOnClickListener {
            bayar()
        }
        cb_langgan.setOnClickListener {


            if (langgan_status == selected){
                langgan_status = unselected
            }
            else {
                langgan_status = selected
            }
//            if (cb_langgan == selected )
//            cb_langgan.isChecked = selected
            adapter.notifyDataSetChanged()
            Log.d("clik cb_langgan()-Pengiriman_Activity", "langgan_status= " + langgan_status )

        }
        cb_kemasan.setOnClickListener{
            if (kemasan_status == selected){
                kemasan_status = unselected
            }
            else {
                kemasan_status = selected
            }
            adapter.notifyDataSetChanged()
            Log.d("clik cb_Kemasan()-Pengiriman_Activity", "Kemasan_status= " + kemasan_status )
        }
        edt_tanggal.setOnClickListener {
            datePickerPengiriman()
        }
    }

    var cal = Calendar.getInstance()
    private fun datePickerPengiriman() {
        var year = 0
        var month = 0
        var day = 0

        calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH) + 5


        val dialog = DatePickerDialog(
            this,
            object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()

//                val myFormat = "dd/MM/yyyy"
//                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
//                edt_tanggal.setText(sdf.format(calendar.time))

//                val myFormat = "dd/MM/yyyy"
//                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
//                val formt= sdf.format(calendar.time)

//                var set = String.format("%d-%d-%d", year, month + 1, dayOfMonth )

            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        val minDateP = Calendar.getInstance()
        minDateP.add(Calendar.DAY_OF_MONTH, 5)

        dialog.datePicker.minDate = minDateP.timeInMillis
        dialog.show()

    }

    @SuppressLint("LongLogTag")
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        var set  = sdf.format(cal.getTime())
        edt_tanggal.setText(set)

        val vwFormat = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(vwFormat, Locale.US)
        var set2  = sdf2.format(cal.getTime())
        tanggal_kirim = set2

        Log.d("updateDateInView()-Pengiriman_Activity", "Tanggal Kirim = " + tanggal_kirim )

    }

    @SuppressLint("LongLogTag")
    private fun bayar() {
        val user = SharedPref(this).getUser()!!

        checkRequire()

        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<Chekout.Product>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.price))

                val produk = Chekout.Product()
                produk.id = "" + p.id
                produk.name = "" + p.name
                produk.qty = "" + p.jumlah
                produk.price = "" + p.price
                produk.total = "" + (p.jumlah * Integer.valueOf(p.price))
                produks.add(produk)
            }
        }

        val chekout = Chekout()
        chekout.customer_id = user.id
        chekout.useplastic = kemasan_status
        chekout.subtotal = totalHarga
        chekout.total_item = totalItem
        chekout.delivery_date = tanggal_kirim
        chekout.delivery_time = getWaktu
        chekout.delivery_price = ongkir
        chekout.note = edt_note.text.toString()
        chekout.address = edt_alamat.text.toString()
        chekout.detailSector_name = sectorDetail_Name
        chekout.scheduled = langgan_status
        chekout.sector_id = sector_Id
        chekout.paymethod = paymethode
        chekout.products = produks

        val json = Gson().toJson(chekout, Chekout::class.java)
        Log.d("Respon:", "jseon:" + json)
        postCheckout(chekout)
    }

    private fun checkRequire() {
        if (edt_alamat.text.isEmpty()) {
            edt_alamat.error = "Kolom alamat tidak boleh kosong"
            edt_alamat.requestFocus()
            return
        } else if (edt_tanggal.text.isEmpty()) {
            edt_tanggal.error = "Kolom tanggal kirim tidak boleh kosong"
            edt_tanggal.requestFocus()
            return
        }else if (sectorDetail_Name == "") {
            text_sector.setError("Area pengiriman  tidak boleh kosong")
            text_sector.requestFocus()
            return
        } else if (getWaktu == "") {
            text_waktu.setError("Waktu kirim tidak boleh kosong")
            text_waktu.requestFocus()
            return
        }

    }

    private fun postCheckout(chekout: Chekout) {
        var chekoutFix = chekout
        checkoutSucces = chekoutFix
        val json = Gson().toJson(chekoutFix, Chekout::class.java)
        Log.d("postCheckout()", "Respon: jseon = " + json)
//        dbClean()

        val loading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loading.setTitleText("Loading...").show()

        ApiConfig.instanceRetrofit.chekout(chekoutFix).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                loading.dismiss()
                error(t.message.toString())
//                Toast.makeText(this, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                loading.dismiss()
                if (!response.isSuccessful) {
                    error(response.message())
                    return
                }

                val respon = response.body()!!
                if (respon.Status == "Success") {

                    val jsTransaksi = Gson().toJson(respon.Transaction, Transaction::class.java)
                    val jsChekout = Gson().toJson(chekout, Chekout::class.java)
                    Log.d("postCheckout()", "Respon: jsChecout = " + jsChekout)
                    Log.d("postCheckout()", "Respon: jsTransaksi = " + jsTransaksi)

                    finisingCheckout(jsTransaksi)
//
//                    val intent = Intent(this@PengirimanActivity, SuccessActivity::class.java)
////                    intent.putExtra("bank", jsBank)
//                    intent.putExtra("transaksi", jsTransaksi)
//                    intent.putExtra("chekout", jsChekout)
//                    startActivity(intent)

                } else {
                    error(respon.Message)
                    Toast.makeText(this@PengirimanActivity, "Error:" + respon.Message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        //                    val jsTransaksi = Gson().toJson(respon.Transaction, Transaction::class.java)
//                    val jsChekout = Gson().toJson(chekoutFix, Chekout::class.java)
//


    }

    private fun finisingCheckout(jsTransaksi: String?) {
        pb_pengiriman.visibility = View.VISIBLE
        dbClean()
        sendNotification()
        val intent = Intent(this@PengirimanActivity, SuccessActivity::class.java)
        intent.putExtra("transaksi", jsTransaksi)
        pb_pengiriman.visibility = View.GONE
        startActivity(intent)
    }

    private fun dbClean() {
        val myDbDelete = MyDatabase.getInstance(this)!!
        for (produk in checkoutSucces.products){
            myDbDelete.daoKeranjang().deleteById(produk.id)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Asik, pesananmu sedang diproses nih!"
            val descriptionText = "cek kembali pesananmu dikeranjang agar tidak lupa~"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("LongLogTag")
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_veganstore)
            .setContentTitle("Asik, pesananmu sedang diproses nih!")
            .setContentText("cek kembali pesananmu dikeranjang agar tidak lupa~")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
        Log.d("sendNotification()-Pengiriman_Activity", "Notifikasi Di kirimkan" )
    }


//    fun setTotal(ongkir: String) {
//        tv_ongkir.text = Helper().gantiRupiah(ongkir)
//        tv_total.text = Helper().gantiRupiah(Integer.valueOf(ongkir) + totalHarga)
//    }

    fun error(pesan: String) {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText(pesan)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
//        chekAlamat()
        displayProduk()
        hitungTotal()
        super.onResume()
    }
}

//@SuppressLint("SetTextI18n")
//fun chekAlamat() {
//
//    if (myDb.daoAlamat().getByStatus(true) != null) {
//        div_alamat.visibility = View.VISIBLE
//        div_kosong.visibility = View.GONE
//        div_metodePengiriman.visibility = View.VISIBLE
//
//        val a = myDb.daoAlamat().getByStatus(true)!!
//        tv_nama.text = a.name
//        tv_phone.text = a.phone
//        tv_alamat.text = a.alamat + ", " + a.kota + ", " + a.kodepos + ", (" + a.type + ")"
//        btn_tambahAlamat.text = "Ubah Alamat"
//
//        getOngkir("JNE")
//    } else {
//        div_alamat.visibility = View.GONE
//        div_kosong.visibility = View.VISIBLE
//        btn_tambahAlamat.text = "Tambah Alamat"
//    }
//}

//        chekout.user_id = "" + user.id
//        chekout.total_item = "" + totalItem
//        chekout.total_harga = "" + totalHarga
//        chekout.name = a.name
//        chekout.phone = a.phone
//        chekout.jasa_pengiriaman = jasaKirim
//        chekout.ongkir = ongkir
//        chekout.kurir = kurir
//        chekout.detail_lokasi = tv_alamat.text.toString()
//        chekout.total_transfer = "" + (totalHarga + Integer.valueOf(ongkir))
//        chekout.produks = produks

//        val intent = Intent(this, PembayaranActivity::class.java)
//        intent.putExtra("extra", json)
//        startActivity(intent)

