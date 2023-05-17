package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.igovern.entity.VITSRecoveryProcessInputEO;

public interface VITSRecoveryProcessInputRepository extends JpaRepository<VITSRecoveryProcessInputEO, Long> {
	
	@Query(value = "SELECT * FROM V_ITS_RECOVERY_PROCESS_INPUT WHERE OVP_ID=?1 ORDER BY ISNULL(CBWK_BWE_DT,'12/31/9999'),OVPDTLS_ID", nativeQuery = true)
	List<VITSRecoveryProcessInputEO> getRecoveryProcessInput(Long ovpId);
}
