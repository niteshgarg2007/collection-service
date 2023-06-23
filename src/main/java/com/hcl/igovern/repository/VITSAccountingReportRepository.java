package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSAccountingReportEO;

@Repository
public interface VITSAccountingReportRepository extends JpaRepository<VITSAccountingReportEO, Long> {
	
}
