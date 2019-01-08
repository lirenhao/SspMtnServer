package com.yada.ssp.mtnServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Request {

    private MsgInfo msgInfo;
    private TrxInfo trxInfo;
    private CertificateSignature certificateSignature;

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

    public CertificateSignature getCertificateSignature() {
        return certificateSignature;
    }

    public void setCertificateSignature(CertificateSignature certificateSignature) {
        this.certificateSignature = certificateSignature;
    }
}
