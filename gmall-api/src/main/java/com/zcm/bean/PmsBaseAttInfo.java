package com.zcm.bean;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class PmsBaseAttInfo implements Serializable {
    private Integer id;

    private String attrName;

    private Integer catalog3Id;

    private String isEnabled;
    @Transient
    private List<PmsBaseAttValue> attValuesList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }

    public List<PmsBaseAttValue> getAttValuesList() {
        return attValuesList;
    }

    public void setAttValuesList(List<PmsBaseAttValue> attValuesList) {
        this.attValuesList = attValuesList;
    }
}
