package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOvpRecvDstReversalEO;

@Repository
public interface VITSOvpRecvDstReversalRepository extends JpaRepository<VITSOvpRecvDstReversalEO, Long> {

	List<VITSOvpRecvDstReversalEO> findByRecoveryDtlsId(Long recoveryDtlsId);
	
}
