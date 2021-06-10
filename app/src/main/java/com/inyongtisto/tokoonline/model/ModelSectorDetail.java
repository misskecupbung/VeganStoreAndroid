package com.inyongtisto.tokoonline.model;

import java.io.Serializable;

public class ModelSectorDetail implements Serializable {

    public int id;
    public String name;
    public String sector_id;
    public Sector sector = new Sector();

    class Sector {
        public int id;
        public String name;
        public String type;
        public String postal_code;
        public int province_id;
        public String created_at;
        public String updated_at;
    }
}
