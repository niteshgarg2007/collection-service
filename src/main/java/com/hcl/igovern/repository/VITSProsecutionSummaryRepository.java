package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSProsecutionSummaryEO;

@Repository
public interface VITSProsecutionSummaryRepository extends JpaRepository<VITSProsecutionSummaryEO, Long> {

	List<VITSProsecutionSummaryEO> findByVictimBadActorXrefId(Long victimBadActorXrefId);
	
}
