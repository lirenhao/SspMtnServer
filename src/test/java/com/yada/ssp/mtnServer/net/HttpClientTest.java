package com.yada.ssp.mtnServer.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yada.ssp.mtnServer.util.DateUtil;
import com.yada.ssp.mtnServer.util.SignUtil;
import com.yada.ssp.mtnServer.view.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

public class HttpClientTest {

    private final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
    private final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void postJson() {
        HttpClient httpClient = new HttpClient();

        Request req = new Request();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setVersionNo("1.0.0");
        msgInfo.setTimeStamp(DateUtil.getCurDateTime());
        msgInfo.setOrgId("0001");
        req.setMsgInfo(msgInfo);

        TrxInfo trxInfo = new TrxInfo();
        trxInfo.setMerchantId("MerchantId");
        trxInfo.setTerminalId("TerminalId");
        DiscountDetail detail = new DiscountDetail();
        detail.setDiscountAmt(new BigInteger("0"));
        detail.setDiscountNote("");
        trxInfo.setDiscountDetails(Collections.singletonList(detail));
        trxInfo.setOriginalAmt(new BigInteger("1"));
        trxInfo.setCostAmt(new BigInteger("1"));
        trxInfo.setCcyCode("702");
        trxInfo.setChannelId("01");
        trxInfo.setMerTraceNo("MerTraceNo");
        trxInfo.setOriginalMerTraceNo("OriginalMerTraceNo");
        trxInfo.setBankLsNo("BankLsNo");
        trxInfo.setChannelTraceNo("ChannelTraceNo");
        trxInfo.setTrxRespCode("TrxRespCode");
        trxInfo.setTrxRespDesc("TrxRespDesc");
        req.setTrxInfo(trxInfo);

        CertificateSignature signature = new CertificateSignature();
        signature.setSignature("00000000");
        req.setCertificateSignature(signature);

        signature.setSignature(SignUtil.sign(req, privateKey));
        req.setCertificateSignature(signature);

        try {
            String reqData = objectMapper.writeValueAsString(req);
            String respData = httpClient.postJson("http://localhost:3000", reqData);

            Response resp = objectMapper.readValue(respData, Response.class);
            resp.setData(respData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}