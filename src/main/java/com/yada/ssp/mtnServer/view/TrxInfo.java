package com.yada.ssp.mtnServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TrxInfo {

    private String merchantId; // 商户号

    private String terminalId; // 终端号

    private BigInteger tranAmt; // 交易金额 单位:分

    private List<DiscountDetail> discountDetails; // 折扣信息

    private BigInteger originalAmt; // 机构金额

    private BigInteger costAmt; // 成本金额

    private String ccyCode; // 交易币种

    private String channelId; // 交易渠道

    private String merTraceNo; // 商户流水号

    private String originalMerTraceNo; // 原消费交易的商户流水号

    private String bankLsNo; // 银行流水号

    private String channelTraceNo; // 二维码渠道对应系统的流水号

    private String trxRespCode; // 原交易的实际响应码

    private String trxRespDesc; // 原交易的实际响应说明

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

    public BigInteger getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigInteger tranAmt) {
        this.tranAmt = tranAmt;
    }

    public List<DiscountDetail> getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(List<DiscountDetail> discountDetails) {
        this.discountDetails = discountDetails;
    }

    public BigInteger getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(BigInteger originalAmt) {
        this.originalAmt = originalAmt;
    }

    public BigInteger getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(BigInteger costAmt) {
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

    public String getBankLsNo() {
        return bankLsNo;
    }

    public void setBankLsNo(String bankLsNo) {
        this.bankLsNo = bankLsNo;
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
