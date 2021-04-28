package com.inyongtisto.tokoonline.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Kategori implements Serializable {

//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "idTb")
//    public int idTb;

    public int id;
    public String name;
    public String description;
    public String image;
    public String slug;
    public String created_at;
    public String updated_at;

}
