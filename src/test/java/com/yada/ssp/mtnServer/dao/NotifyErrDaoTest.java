package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.NotifyErr;
import com.yada.ssp.mtnServer.util.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotifyErrDaoTest {

    @Autowired
    private NotifyErrDao notifyErrDao;

    @Before
    public void before() {
        NotifyErr err1 = new NotifyErr();
        err1.setLsId("lsId1");
        err1.setMerNo("merNo");
        err1.setDateTime(DateUtil.getCurDateTime());
        err1.setRetryNo(1);
        notifyErrDao.save(err1);

        NotifyErr err2 = new NotifyErr();
        err2.setLsId("lsId2");
        err2.setMerNo("merNo");
        err2.setDateTime(DateUtil.getCurDateTime());
        err2.setRetryNo(2);
        notifyErrDao.save(err2);
    }

    @After
    public void after() {
        notifyErrDao.deleteAll();
    }

    @Test
    public void testFindByRetryNoLessThanEqual() {
        List<NotifyErr> errs =  notifyErrDao.findByRetryNoLessThanEqual(1);
        Assert.assertEquals(1, errs.size());
        Assert.assertEquals("lsId1", errs.get(0).getLsId());
        Assert.assertEquals(1, errs.get(0).getRetryNo());
    }

}