package com.yada.ssp.mtnServer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yada.ssp.mtnServer.dao.ApiOrgDao;
import com.yada.ssp.mtnServer.dao.TransDao;
import com.yada.ssp.mtnServer.model.ApiOrg;
import com.yada.ssp.mtnServer.model.Trans;
import com.yada.ssp.mtnServer.net.HttpClient;
import com.yada.ssp.mtnServer.util.DateUtil;
import com.yada.ssp.mtnServer.util.SignUtil;
import com.yada.ssp.mtnServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

@Service
public class NotifyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper = new ObjectMapper();

    private ApiOrgDao apiOrgDao;
    private TransDao transDao;
    private HttpClient httpClient;
    private NotifyErrService notifyErrService;

    @Autowired
    public NotifyService(ApiOrgDao apiOrgDao, TransDao transDao,
                         HttpClient httpClient, NotifyErrService notifyErrService) {
        this.apiOrgDao = apiOrgDao;
        this.transDao = transDao;
        this.httpClient = httpClient;
        this.notifyErrService = notifyErrService;
    }

    @Async
    public void send(String merNo, String lsId) {
        ApiOrg apiOrg = apiOrgDao.findByMerchants_MerNo(merNo).orElse(null);
        if (apiOrg != null && apiOrg.getNotifyUrl() != null) {
            Trans trans = transDao.findById(lsId).orElse(null);
            if (trans != null) {
                Request req = pkgRequest(apiOrg, trans);
                try {
                    String reqData = objectMapper.writeValueAsString(req);
                    logger.info("商户[{}]交易通知发送信息[{}]", merNo, reqData);

                    String respData = httpClient.postJson(apiOrg.getNotifyUrl(), reqData);
                    logger.info("商户[{}]交易通知返回信息[{}]", merNo, respData);

                    Response resp = objectMapper.readValue(respData, Response.class);
                    resp.setData(respData);
                    sendSuccess(merNo, lsId, apiOrg, resp);
                } catch (IOException e) {
                    logger.warn("商户[{}]交易[{}]通知失败,异常信息是[{}]", merNo, lsId, e.getMessage());
                    notifyErrService.next(merNo, lsId);
                }
            } else {
                logger.warn("商户[{}]没有找到流水号[{}]的交易", merNo, lsId);
                notifyErrService.next(merNo, lsId);
            }
        } else {
            logger.warn("商户[{}]没有找到对应的API机构通知地址", merNo);
        }
    }

    private Request pkgRequest(ApiOrg apiOrg, Trans trans) {
        Request req = new Request();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setVersionNo("1.0.0");
        msgInfo.setTimeStamp(DateUtil.getCurDateTime());
        msgInfo.setOrgId(apiOrg.getOrgId());
        req.setMsgInfo(msgInfo);

        TrxInfo trxInfo = new TrxInfo();
        trxInfo.setMerchantId(trans.getMerchantId());
        trxInfo.setTerminalId(trans.getTerminalId());
        trxInfo.setTranAmt(pkgAmount(trans.getTranAmt()));
        DiscountDetail detail = new DiscountDetail();
        detail.setDiscountAmt(pkgAmount(trans.getDiscountAmt()));
        detail.setDiscountNote(trans.getDiscountNote());
        trxInfo.setDiscountDetails(Collections.singletonList(detail));
        trxInfo.setOriginalAmt(pkgAmount(trans.getOriginalAmt()));
        trxInfo.setCostAmt(pkgAmount(trans.getCostAmt()));
        trxInfo.setCcyCode(trans.getCcyCode());
        trxInfo.setChannelId(trans.getChannelId());
        trxInfo.setMerTraceNo(trans.getMerTraceNo());
        trxInfo.setOriginalMerTraceNo(trans.getOriginalMerTraceNo());
        trxInfo.setBankLsNo(trans.getLsId());
        trxInfo.setChannelTraceNo(trans.getChannelTraceNo());
        trxInfo.setTrxRespCode(trans.getTrxRespCode());
        trxInfo.setTrxRespDesc(trans.getTrxRespDesc());
        req.setTrxInfo(trxInfo);

        CertificateSignature signature = new CertificateSignature();
        signature.setSignature("00000000");
        req.setCertificateSignature(signature);

        signature.setSignature(SignUtil.sign(req, apiOrg.getPrivateKey()));
        req.setCertificateSignature(signature);
        return req;
    }

    private BigInteger pkgAmount(String amt) {
        if (amt != null && !"".equals(amt)) {
            return new BigInteger(amt).multiply(BigInteger.valueOf(100));
        } else {
            return null;
        }
    }

    private void sendSuccess(String merNo, String lsId, ApiOrg apiOrg, Response resp) {
        assert apiOrg != null;
        if (apiOrg.getPublicKey() != null) {
            String sign = resp.getCertificateSignature().getSignature();
            String data = resp.getData().replace(sign, "00000000");
            if (SignUtil.verify(data, sign, apiOrg.getPublicKey())
                    && resp.getMsgResponse() != null
                    && "00".equals(resp.getMsgResponse().getRespCode())) {
                notifyErrService.delete(lsId);
            } else {
                logger.warn("商户[{}]交易通知返回[{}]验签失败", merNo);
                notifyErrService.next(merNo, lsId);
            }
        } else {
            logger.warn("机构[{}]没有找到对应的公钥,验签失败", apiOrg.getOrgId());
            notifyErrService.next(merNo, lsId);
        }
    }

}
