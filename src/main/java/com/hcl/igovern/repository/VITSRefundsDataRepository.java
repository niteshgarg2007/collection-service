package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSRefundsDataEO;

@Repository
public interface VITSRefundsDataRepository extends JpaRepository<VITSRefundsDataEO, Long> {

	List<VITSRefundsDataEO> findByBadActorId(Long badActorId);
	
}
