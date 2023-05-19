package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSDistributionReversalEO;

@Repository
public interface VITSDistributionReversalRepository extends JpaRepository<VITSDistributionReversalEO, Long> {

	List<VITSDistributionReversalEO> findByOvpdtlsId(Long ovpdtlsId);
	
}
