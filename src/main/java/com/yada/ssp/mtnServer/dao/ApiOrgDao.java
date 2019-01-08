package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.ApiOrg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiOrgDao extends JpaRepository<ApiOrg, String> {

    Optional<ApiOrg> findByMerchants_MerNo(String merNo);
}