package com.inyongtisto.tokoonline.model

class Transaction {

    lateinit var invoice : String
    lateinit var customer_name : String
    lateinit var customer_phone : String
    var useplastic = 0
    var subtotal = 0
    var total_item = 0
    var delivery_price = 0
    lateinit var delivery_time : String
    lateinit var delivery_date : String
    var note : String = ""
    lateinit var address : String
    lateinit var detailSector_name : String
    var scheduled = 9
    lateinit var status : String
    var sector_id = 0
    var total = 0
    lateinit var paymethod : String
    lateinit var updated_at: String
    lateinit var created_at : String
    var id = 0

    val order_detail = ArrayList<order_detail>()
    val customer = CustomerDetail()

}