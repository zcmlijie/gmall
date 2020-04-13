package com.zcm.service;

import com.zcm.bean.PmsBaseCatalong1;
import com.zcm.bean.PmsBaseCatalong2;
import com.zcm.bean.PmsBaseCatalong3;

import java.util.List;

public interface PmsBaseCatalogService {
    List<PmsBaseCatalong1> getPmsBaseCatalog1();

    List<PmsBaseCatalong2> getPmsBaseCatalog2(Integer id2);

    List<PmsBaseCatalong3> getPmsBaseCatalog3(Integer id3);
}
