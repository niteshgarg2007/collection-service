package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSRecoveryUpdateEO;

@Repository
public interface VITSRecoveryUpdateRepository extends JpaRepository<VITSRecoveryUpdateEO, Long> {

	List<VITSRecoveryUpdateEO> findByBadActorId(Long badActorId);

	List<VITSRecoveryUpdateEO> findByVictimBadActorXrefId(Long victimBadActorXrefId);

	List<VITSRecoveryUpdateEO> findByBadActorIdAndRecoveryId(Long badActorId, Long recoveryId);

	List<VITSRecoveryUpdateEO> findByVictimBadActorXrefIdAndRecoveryId(Long victimBadActorXrefId, Long recoveryId);

	List<VITSRecoveryUpdateEO> findByVictimBadActorXrefIdAndRecoveryIdIsNull(Long victimBadActorXrefId);

	List<VITSRecoveryUpdateEO> findByBadActorIdAndRecoveryIdIsNull(Long badActorId);
	
	@Query(value = "SELECT * FROM V_ITS_RECOVERY_UPDATE WHERE BAD_ACTOR_ID=?1 AND (RECOVERY_ID=?2 OR RECOVERY_ID IS NULL)", nativeQuery = true)
	List<VITSRecoveryUpdateEO> getVITSRecoveryUpdateEOByBadActorList(Long badActorId, Long recoveryId);
	
	@Query(value = "SELECT * FROM V_ITS_RECOVERY_UPDATE WHERE VICTIM_BAD_ACTOR_XREF_ID=?1 AND (RECOVERY_ID=?2 OR RECOVERY_ID IS NULL)", nativeQuery = true)
	List<VITSRecoveryUpdateEO> getVITSRecoveryUpdateEOByXrefList(Long victimBadActorXrefId, Long recoveryId);
}
