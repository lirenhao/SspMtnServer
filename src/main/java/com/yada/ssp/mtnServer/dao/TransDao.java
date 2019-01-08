package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.Trans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransDao extends JpaRepository<Trans, String> {

}
