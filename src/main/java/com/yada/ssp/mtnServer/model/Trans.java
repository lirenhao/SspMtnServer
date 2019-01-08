package com.yada.ssp.mtnServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_V_TRANS")
public class Trans {

    @Id
    @Column(name = "BANK_LS_NO")
    private String lsId;
    @Column(name = "MER_NO")
    private String merchantId;
    @Column(name = "TERM_NO")
    private String terminalId;
    @Column(name = "TRAN_AMT")
    private String tranAmt;
    @Column(name = "DISCOUNT_AMT")
    private String discountAmt;
    @Column(name = "DISCOUNT_NOTE")
    private String discountNote;
    @Column(name = "ORIGINAL_AMT")
    private String originalAmt;
    @Column(name = "COST_AMT")
    private String costAmt;
    @Column(name = "CCY_CODE")
    private String ccyCode;
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Column(name = "MER_TRACE_NO")
    private String merTraceNo;
    @Column(name = "ORIGINAL_MER_TRACE_NO")
    private String originalMerTraceNo;
    @Column(name = "CHANNEL_TRACE_NO")
    private String channelTraceNo;
    @Column(name = "TRX_RESP_CODE")
    private String trxRespCode;
    @Column(name = "TRX_RESP_DESC")
    private String trxRespDesc;

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getDiscountNote() {
        return discountNote;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
    }

    public String getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(String originalAmt) {
        this.originalAmt = originalAmt;
    }

    public String getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(String costAmt) {
        this.costAmt = costAmt;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMerTraceNo() {
        return merTraceNo;
    }

    public void setMerTraceNo(String merTraceNo) {
        this.merTraceNo = merTraceNo;
    }

    public String getOriginalMerTraceNo() {
        return originalMerTraceNo;
    }

    public void setOriginalMerTraceNo(String originalMerTraceNo) {
        this.originalMerTraceNo = originalMerTraceNo;
    }

    public String getChannelTraceNo() {
        return channelTraceNo;
    }

    public void setChannelTraceNo(String channelTraceNo) {
        this.channelTraceNo = channelTraceNo;
    }

    public String getTrxRespCode() {
        return trxRespCode;
    }

    public void setTrxRespCode(String trxRespCode) {
        this.trxRespCode = trxRespCode;
    }

    public String getTrxRespDesc() {
        return trxRespDesc;
    }

    public void setTrxRespDesc(String trxRespDesc) {
        this.trxRespDesc = trxRespDesc;
    }
}
