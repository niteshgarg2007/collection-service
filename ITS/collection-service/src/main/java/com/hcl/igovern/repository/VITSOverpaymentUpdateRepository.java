package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOverpaymentUpdateEO;

@Repository
public interface VITSOverpaymentUpdateRepository extends JpaRepository<VITSOverpaymentUpdateEO, Long> {

}
