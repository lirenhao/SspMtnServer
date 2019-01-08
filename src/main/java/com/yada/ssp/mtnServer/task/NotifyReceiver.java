package com.yada.ssp.mtnServer.task;

import com.yada.ssp.mtnServer.config.MqProperties;
import com.yada.ssp.mtnServer.model.NotifyErr;
import com.yada.ssp.mtnServer.service.NotifyErrService;
import com.yada.ssp.mtnServer.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotifyReceiver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MqProperties mqProperties;
    private NotifyService notifyService;
    private NotifyErrService notifyErrService;

    @Autowired
    public NotifyReceiver(MqProperties mqProperties, NotifyService notifyService, NotifyErrService notifyErrService) {
        this.mqProperties = mqProperties;
        this.notifyService = notifyService;
        this.notifyErrService = notifyErrService;
    }

    @JmsListener(destination = "${mq.tranQueue}", containerFactory = "mqFactory")
    public void tranNotify(String msg) {
        logger.info("MQ获取的信息[{}]", msg);
        try {
            Map<String, String> tran = strToMap(msg);
            notifyErrService.setSending(tran.get("lsId"));
            notifyService.send(tran.get("merNo"), tran.get("lsId"));
        } catch (Exception e) {
            logger.warn("MQ获取数据解析失败,数据是[{}],异常信息是[{}]", msg, e.getMessage());
        }
    }

    /**
     * 解析数据
     *
     * @param data MQ获取的数据
     * @return 字段映射
     */
    private Map<String, String> strToMap(String data) throws Exception {
        // 商户号、交易时间(YYYYMMDDmmhhss)、支付方式、交易渠道、交易金额、交易币种、交易单号、检索参考号
        String[] fields = mqProperties.getDataField();
        Map<String, String> tran = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(mqProperties.getDataRegex());
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            for (int i = 0; i < fields.length; i++) {
                tran.put(fields[i], matcher.group(i + 1).trim());
            }
        } else {
            logger.warn("MQ获取数据解析失败,数据是[{}]", data);
            throw new Exception("MQ获取数据解析失败");
        }
        return tran;
    }

    @Scheduled(fixedRateString = "${notify.err.queryRate}")
    public void throwNotify() {
        List<NotifyErr> errList = notifyErrService.getNotifyErr();
        for (NotifyErr err : errList) {
            logger.info("数据库获取的发送错误信息 >>>[{}]->[{}]", err.getMerNo(), err.getLsId());
            if (!notifyErrService.isSending(err.getLsId())) {
                notifyErrService.setSending(err.getLsId());
                notifyService.send(err.getMerNo(), err.getLsId());
            } else {
                logger.info("发送错误信息已经在发送 >>>[{}]->[{}]", err.getMerNo(), err.getLsId());
            }
        }
    }
}
