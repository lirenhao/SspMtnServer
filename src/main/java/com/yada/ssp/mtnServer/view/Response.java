package com.yada.ssp.mtnServer.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {

    private MsgInfo msgInfo;

    private TrxInfo trxInfo;

    private MsgResponse msgResponse;

    private CertificateSignature certificateSignature;

    @JsonIgnore
    private String data; // 原始报文

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public TrxInfo getTrxInfo() {
        return trxInfo;
    }

    public void setTrxInfo(TrxInfo trxInfo) {
        this.trxInfo = trxInfo;
    }

    public MsgResponse getMsgResponse() {
        return msgResponse;
    }

    public void setMsgResponse(MsgResponse msgResponse) {
        this.msgResponse = msgResponse;
    }

    public CertificateSignature getCertificateSignature() {
        return certificateSignature;
    }

    public void setCertificateSignature(CertificateSignature certificateSignature) {
        this.certificateSignature = certificateSignature;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
