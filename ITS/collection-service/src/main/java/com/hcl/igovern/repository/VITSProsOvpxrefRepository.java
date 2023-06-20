package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSProsOvpxrefEO;

@Repository
public interface VITSProsOvpxrefRepository extends JpaRepository<VITSProsOvpxrefEO, Long> {

	List<VITSProsOvpxrefEO> findByProsId(Long selectedProsecutionId);

}
