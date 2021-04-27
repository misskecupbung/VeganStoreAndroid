package com.inyongtisto.tokoonline.model

class ResponModel {
    var success = 0
    var Status = "Failed"

    lateinit var Message: String

    var user = User()
    var Products: ArrayList<Produk> = ArrayList()
    var Categories : ArrayList<Kategori> = ArrayList()

    var rajaongkir = ModelAlamat()

    var provinsi: ArrayList<ModelAlamat> = ArrayList()
    var kota_kabupaten: ArrayList<ModelAlamat> = ArrayList()
    var kecamatan: ArrayList<ModelAlamat> = ArrayList()
}