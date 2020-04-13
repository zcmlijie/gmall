package com.zcm.bean;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * 平台的销售属性
 */
public class PmsBaseSaleAtt implements Serializable {
    private static final long serialVersionUID = 9169740843534378747L;
    private Integer id;
    private  String NAME;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
