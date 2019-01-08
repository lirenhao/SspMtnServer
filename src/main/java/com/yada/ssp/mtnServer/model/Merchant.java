package com.yada.ssp.mtnServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_B_MERCHANT_BASE_GAS")
public class Merchant {

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
}
