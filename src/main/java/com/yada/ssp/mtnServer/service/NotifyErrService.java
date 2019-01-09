package com.yada.ssp.mtnServer.service;

import com.yada.ssp.mtnServer.dao.NotifyErrDao;
import com.yada.ssp.mtnServer.model.NotifyErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotifyErrService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private final NotifyErrDao notifyErrDao;
    private final List<String> sendList = new ArrayList<>();

    @Value("${notify.err.notifyRate}")
    private int[] notifyRate;

    @Autowired
    public NotifyErrService(NotifyErrDao notifyErrDao) {
        this.notifyErrDao = notifyErrDao;
    }

    /**
     * 存储错误处理的下一次处理数据
     *
     * @param merNo 商户号
     * @param lsId  流水号
     */
    void next(String merNo, String lsId) {
        NotifyErr err = notifyErrDao.findById(lsId).orElse(new NotifyErr());
        if (err.getRetryNo() > notifyRate.length) {
            notifyErrDao.deleteById(lsId);
        } else {
            err.setLsId(lsId);
            err.setMerNo(merNo);
            err.setDateTime(sdf.format(new Date()));
            err.setRetryNo(err.getRetryNo() + 1);
            notifyErrDao.saveAndFlush(err);
        }
        // 发送结束删除发送状态
        sendList.remove(lsId);
    }

    /**
     * 获取没有超过次数没有过期的上次发送错误的数据
     *
     * @return 符合条件的发送错误数据列表
     */
    public List<NotifyErr> getNotifyErr() {
        return notifyErrDao.findByRetryNoLessThanEqual(notifyRate.length)
                .stream().filter(this::isNotify).collect(Collectors.toList());
    }

    boolean isNotify(NotifyErr err) {
        if (notifyRate.length < err.getRetryNo()) {
            return false;
        } else {
            try {
                long curTime = System.currentTimeMillis();
                long expTime = sdf.parse(err.getDateTime()).getTime()
                        + notifyRate[err.getRetryNo() - 1] * 1000;
                return curTime > expTime;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    /**
     * 发送成功后删除发送错误数据
     *
     * @param id 错误数据ID
     */
    void delete(String id) {
        if (null != id && !"".equals(id)) {
            notifyErrDao.findById(id).ifPresent(notifyErrDao::delete);
        }
        // 发送结束删除发送状态
        sendList.remove(id);
    }

    /**
     * 数据是否正在发送
     *
     * @param id 　数据ID
     * @return true是正在发送
     */
    public boolean isSending(String id) {
        return sendList.indexOf(id) >= 0;
    }

    /**
     * 设置数据正在发送
     *
     * @param id 　数据ID
     */
    public void setSending(String id) {
        sendList.add(id);
    }
}
