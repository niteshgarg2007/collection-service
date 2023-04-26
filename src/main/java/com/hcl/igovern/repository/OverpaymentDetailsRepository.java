package com.hcl.igovern.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsOverpaymentDetailsEO;

@Repository
public interface OverpaymentDetailsRepository extends JpaRepository<ItsOverpaymentDetailsEO, Long> {

	ItsOverpaymentDetailsEO findByItsOverpaymentAndCbwkBweDtAndClmId(Long ovpId, Timestamp cbwkBweDt, Long clmId);

}
