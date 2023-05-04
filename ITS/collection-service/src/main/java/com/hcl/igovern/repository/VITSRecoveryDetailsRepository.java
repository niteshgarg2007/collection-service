package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSRecoverySummaryEO;

@Repository
public interface VITSRecoveryDetailsRepository extends JpaRepository<VITSRecoverySummaryEO, Long> {

	List<VITSRecoverySummaryEO> findByVictimBadActorXrefId(Long victimBadActorXrefId);
	
}
