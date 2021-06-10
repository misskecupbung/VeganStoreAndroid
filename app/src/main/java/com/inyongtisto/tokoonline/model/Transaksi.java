package com.inyongtisto.tokoonline.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaksi implements Serializable {
    public String invoice = "";
    public String customer_name = "";
    public String customer_phone = "";
    public int useplastic = 0;
    public int subtotal = 0;
    public int total_item = 0;
    public int delivery_price = 0;
    public String delivery_date = "";
    public String delivery_time = "";
    public String note = "";
    public String address = "";
    public String detailSector_name = "";
    public int scheduled = 0;
    public String status = "";
    public int sector_id = 0;
    public int total = 0;
    public String paymethod = "";
    public String updated_at = "";
    public String created_at = "";
    public int id = 0;

    public ArrayList order_detail = new ArrayList<order_detail>();
    public CustomerDetail customer = new CustomerDetail();
}

//class Transaksi {
//    var invoice = ""
//    var customer_name = ""
//    var customer_phone = ""
//    var useplastic = false
//    var subtotal = 0
//    var total_item = 0
//    var delivery_price = 0
//    var delivery_date = ""
//    var note = ""
//    var address = ""
//    var detailSector_name = ""
//    var scheduled : Boolean = false
//    var status = ""
//    var sector_id = 0
//    var total = 0
//    var paymethod = ""
//    var updated_at = ""
//    var created_at = ""
//    var id = 0
//
//    //    var id = 0
////    var bank = ""
////    var jasa_pengiriaman = ""
////    var kurir = ""
////    var name = ""
////    var ongkir = ""
////    var phone = ""
////    var total_harga = ""
////    var total_transfer = ""
////    var detail_lokasi = ""
////    var user_id = ""
////    var kode_payment = ""
////    var kode_trx = ""
////    var kode_unik = 0
////    var status = ""
////    var expired_at = ""
////    var updated_at = ""
////    var created_at = ""
//    val details = ArrayList<order_detail>()
//    val CustomerDetail customer = CustomerDetail()
//}
