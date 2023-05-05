package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSRecoveryUpdateEO;

@Repository
public interface VITSRecoveryUpdateRepository extends JpaRepository<VITSRecoveryUpdateEO, Long> {
	
}
