package com.inyongtisto.tokoonline.model

class ResponModel {
    var success = 0
    var Status = "Failed"

    lateinit var Message: String

    var Customer = Customer()
    var Products: ArrayList<Produk> = ArrayList()
    var Categories : ArrayList<Kategori> = ArrayList()
    var SectorDetails : ArrayList<ModelSectorDetail> = ArrayList()

    var rajaongkir = ModelAlamat()

//    var Transaksi = Transaksi()
    var Transaction = Transaction()
    var Transactions : ArrayList<Transaction> = ArrayList()

    var provinsi: ArrayList<ModelAlamat> = ArrayList()
    var kota_kabupaten: ArrayList<ModelAlamat> = ArrayList()
    var kecamatan: ArrayList<ModelAlamat> = ArrayList()
}