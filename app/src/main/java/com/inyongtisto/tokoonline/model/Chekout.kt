package com.inyongtisto.tokoonline.model

class Chekout {
    var customer_id: Int = 0
    var useplastic : Int = 0
    var subtotal : Int = 0
    var total_item : Int = 0
    lateinit var delivery_date : String
    lateinit var delivery_time : String
    var delivery_price : Int = 0
    lateinit var note : String
    lateinit var address : String
    lateinit var detailSector_name : String
    var scheduled : Int = 0
    var sector_id : Int = 0
    lateinit var paymethod  : String

    var products = ArrayList<Product>()

    class Product {
        lateinit var id: String
        lateinit var name: String
        lateinit var price: String
        lateinit var qty: String
        lateinit var total: String
    }
}


//lateinit var total_harga: String
//lateinit var name: String
//lateinit var phone: String
//lateinit var kurir: String
//lateinit var detail_lokasi: String
//lateinit var jasa_pengiriaman: String
//lateinit var ongkir: String
//lateinit var total_transfer: String
//lateinit var bank: String