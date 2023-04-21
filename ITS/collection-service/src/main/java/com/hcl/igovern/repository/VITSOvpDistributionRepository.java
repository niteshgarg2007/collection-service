package com.hcl.igovern.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOvpDistributionEO;

@Repository
public interface VITSOvpDistributionRepository extends JpaRepository<VITSOvpDistributionEO, Long> {

	List<VITSOvpDistributionEO> findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(Long victimBadActorXrefId,Timestamp cbwkBweDt, Long claimId);

}
