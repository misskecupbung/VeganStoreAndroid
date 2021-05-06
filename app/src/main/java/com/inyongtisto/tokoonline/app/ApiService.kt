package com.inyongtisto.tokoonline.app

import com.inyongtisto.tokoonline.model.Produk
import com.inyongtisto.tokoonline.model.ResponModel
import com.inyongtisto.tokoonline.model.rajaongkir.ResponOngkir
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

//    Regitrasi
    @FormUrlEncoded
    @POST("customer/regristrasi")
    fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("phone") nomortlp: String,
            @Field("password") password: String
    ): Call<ResponModel>

//    Login
    @FormUrlEncoded
    @POST("customer/login")
    fun login(
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<ResponModel>

//    Product
//      Get All
    @GET("product")
    fun getProduk(): Call<ResponModel>
//      Search
    @GET("product/search/{keyword}")
    fun getSearchProduk(
        @Path(value="keyword") keyword: String
    ): Call<ResponModel>
//      Category
    @GET("product/category/{category}")
    fun getCategoryChoose(
        @Path(value="category") category: String
    ): Call<ResponModel>


    //    Product
//      Get All
    @GET("profil/{id}")
    fun getProfil(
        @Path(value="keyword") keyword: String
    ): Call<ResponModel>

//    Category
//      Get Category
    @GET("category")
    fun getKategori(): Call<ResponModel>


    @GET("province")
    fun getProvinsi(
            @Header("key") key: String
    ): Call<ResponModel>

    @GET("city")
    fun getKota(
            @Header("key") key: String,
            @Query("province") id: String
    ): Call<ResponModel>

    @GET("kecamatan")
    fun getKecamatan(
            @Query("id_kota") id: Int
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("cost")
    fun ongkir(
            @Header("key") key: String,
            @Field("origin") origin: String,
            @Field("destination") destination: String,
            @Field("weight") weight: Int,
            @Field("courier") courier: String
    ): Call<ResponOngkir>
}