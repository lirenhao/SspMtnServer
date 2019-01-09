package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.NotifyErr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifyErrDao extends JpaRepository<NotifyErr, String> {

    List<NotifyErr> findByRetryNoLessThanEqual(int retryNo);
}
