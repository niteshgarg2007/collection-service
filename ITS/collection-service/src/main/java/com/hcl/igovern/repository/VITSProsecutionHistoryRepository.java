package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSProsecutionHistoryEO;

@Repository
public interface VITSProsecutionHistoryRepository extends JpaRepository<VITSProsecutionHistoryEO, Long> {

	List<VITSProsecutionHistoryEO> findByProsId(Long selectedProsId);

}
