package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.igovern.entity.VITSOverpaymentDetailsEO;

public interface VITSOverpaymentDetailsRepository extends JpaRepository<VITSOverpaymentDetailsEO, Long> {

	List<VITSOverpaymentDetailsEO> findByOvpIdAndOvptypCd(Long selectedOverpaymentId, String ovptypCd);

}
