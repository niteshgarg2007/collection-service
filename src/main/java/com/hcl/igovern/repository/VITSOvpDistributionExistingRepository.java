package com.hcl.igovern.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOvpDistributionExistingEO;

@Repository
public interface VITSOvpDistributionExistingRepository extends JpaRepository<VITSOvpDistributionExistingEO, Long> {

	List<VITSOvpDistributionExistingEO> findByOvpIdAndCbwkBweDtAndClmId(Long ovpId, Timestamp cbwkBweDt, Long clmId);

}
