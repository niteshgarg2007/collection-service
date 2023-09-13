package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;

@Repository
public interface VITSOverpaidWeeksUpdateRepository extends JpaRepository<VITSOverpaidWeeksUpdateEO, Long> {

	List<VITSOverpaidWeeksUpdateEO> findByVictimBadActorXrefIdAndClmIdAndIsCancelledAndOvpIdOrderByCbwkBweDt(Long victimBadActorXrefId, Long inputClaimId, String isCancelled, Long ovpId);
	
	@Query(value = "SELECT * FROM V_ITS_OVERPAID_WEEKS_UPDATE WHERE VICTIM_BAD_ACTOR_XREF_ID=?1 AND CLM_ID=?2 AND IS_CANCELLED=?3 AND (OVP_ID=?4 OR OVP_ID IS NULL) ORDER BY CBWK_BWE_DT", nativeQuery = true)
	List<VITSOverpaidWeeksUpdateEO> getOverpaidWeeksForUpdate(Long victimBadActorXrefId, Long inputClaimId, String isCancelled, Long ovpId);
}
