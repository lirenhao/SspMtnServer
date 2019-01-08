package com.yada.ssp.mtnServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_L_API_NOTIFY_ERR")
public class NotifyErr {

    @Id
    @Column
    private String lsId;
    // 商户号
    @Column
    private String merNo;
    // 上次发送的时间
    @Column
    private String dateTime;
    // 重复发送的次数
    @Column
    private int retryNo;

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getRetryNo() {
        return retryNo;
    }

    public void setRetryNo(int retryNo) {
        this.retryNo = retryNo;
    }
}
