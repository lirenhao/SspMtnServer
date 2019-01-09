package com.yada.ssp.mtnServer.service;

import com.yada.ssp.mtnServer.dao.NotifyErrDao;
import com.yada.ssp.mtnServer.model.NotifyErr;
import com.yada.ssp.mtnServer.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotifyErrService.class)
public class NotifyErrServiceTest {

    @MockBean
    private NotifyErrDao notifyErrDao;
    @Autowired
    private NotifyErrService notifyErrService;

    @Test
    public void testNextSave() {
        Mockito.when(notifyErrDao.findById("lsId")).thenReturn(Optional.empty());
        notifyErrService.next("merNo", "lsId");
        Mockito.verify(notifyErrDao, Mockito.times(0)).deleteById("lsId");
        Mockito.verify(notifyErrDao, Mockito.times(1)).saveAndFlush(Mockito.any(NotifyErr.class));
    }

    @Test
    public void testNextDel() {
        NotifyErr notifyErr = new NotifyErr();
        notifyErr.setLsId("lsId");
        notifyErr.setMerNo("merNo");
        notifyErr.setDateTime(DateUtil.getCurDateTime());
        notifyErr.setRetryNo(10);
        Mockito.when(notifyErrDao.findById("lsId")).thenReturn(Optional.of(notifyErr));
        notifyErrService.next("merNo", "lsId");
        Mockito.verify(notifyErrDao, Mockito.times(1)).deleteById("lsId");
        Mockito.verify(notifyErrDao, Mockito.times(0)).saveAndFlush(Mockito.any(NotifyErr.class));
    }

    @Test
    public void testIsNotifyLessRetryNo() {
        NotifyErr notifyErr = new NotifyErr();
        notifyErr.setRetryNo(10);
        Assert.assertFalse(notifyErrService.isNotify(notifyErr));
    }

    @Test
    public void testIsNotifyParseException() {
        NotifyErr notifyErr = new NotifyErr();
        notifyErr.setDateTime("dateTime");
        Assert.assertFalse(notifyErrService.isNotify(notifyErr));
    }

    @Test
    public void testIsNotifyLessCurDate() {
        NotifyErr notifyErr = new NotifyErr();
        notifyErr.setDateTime(DateUtil.getCurDateTime());
        notifyErr.setRetryNo(1);
        Assert.assertFalse(notifyErrService.isNotify(notifyErr));
    }

    @Test
    public void testIsNotifyTrue() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        NotifyErr notifyErr = new NotifyErr();
        String dateTime = sdf.format(new Date(System.currentTimeMillis() - 15 * 1000));
        notifyErr.setDateTime(dateTime);
        notifyErr.setRetryNo(1);
        Assert.assertTrue(notifyErrService.isNotify(notifyErr));
    }
}