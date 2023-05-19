package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSTransactionReversalEO;

@Repository
public interface VITSTransactionReversalRepository extends JpaRepository<VITSTransactionReversalEO, Long> {

	List<VITSTransactionReversalEO> getByOvpId(Long ovpId);

	
}
