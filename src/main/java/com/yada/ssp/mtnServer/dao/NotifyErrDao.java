package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.NotifyErr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotifyErrDao extends JpaRepository<NotifyErr, String> {

    @Query("select err.lsId, err.merNo, err.dateTime, err.retryNo from NotifyErr err where err.retryNo <= ?1")
    List<Object[]> findNotifyErr(int retryNo);

    List<NotifyErr> findByRetryNoLessThanEqual(int retryNo);
}
