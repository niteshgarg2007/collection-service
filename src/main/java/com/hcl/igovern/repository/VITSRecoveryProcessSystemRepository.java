package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSRecoveryProcessSystemEO;

@Repository
public interface VITSRecoveryProcessSystemRepository extends JpaRepository<VITSRecoveryProcessSystemEO, Long> {
	
	@Query(value = "SELECT * FROM V_ITS_RECOVERY_PROCESS_SYSTEM WHERE VICTIM_BAD_ACTOR_XREF_ID=?1 ORDER BY DATE_CREATED,ISNULL(CBWK_BWE_DT,'12/31/9999')", nativeQuery = true)
	List<VITSRecoveryProcessSystemEO> getOverpayentDataByAssociation(Long victimBadActorXrefId);

	@Query(value = "SELECT * FROM V_ITS_RECOVERY_PROCESS_SYSTEM WHERE BAD_ACTOR_ID=?1 ORDER BY PRTY_ID,DATE_CREATED,ISNULL(CBWK_BWE_DT,'12/31/9999')", nativeQuery = true)
	List<VITSRecoveryProcessSystemEO> getOverpayentDataByBadActor(Long badActorId);
	
}
