package com.yada.ssp.mtnServer.dao;

import com.yada.ssp.mtnServer.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantDao extends JpaRepository<Merchant, String> {
    
}