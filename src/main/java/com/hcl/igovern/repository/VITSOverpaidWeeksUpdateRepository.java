package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;

@Repository
public interface VITSOverpaidWeeksUpdateRepository extends JpaRepository<VITSOverpaidWeeksUpdateEO, Long> {

	List<VITSOverpaidWeeksUpdateEO> findByVictimBadActorXrefIdAndClmIdOrderByCbwkBweDt(Long victimBadActorXrefId, Long inputClaimId);

	List<VITSOverpaidWeeksUpdateEO> findByVictimBadActorXrefIdAndClmIdAndOvpIdOrderByCbwkBweDt(Long victimBadActorXrefId, Long inputClaimId, Long ovpId);
	
	@Query(value = "SELECT * FROM V_ITS_OVERPAID_WEEKS_UPDATE WHERE VICTIM_BAD_ACTOR_XREF_ID=?1 AND CLM_ID=?2 AND (OVP_ID=?3 OR OVP_ID IS NULL)", nativeQuery = true)
	List<VITSOverpaidWeeksUpdateEO> getOverpaidWeeksForUpdate(Long victimBadActorXrefId, Long inputClaimId, Long ovpId);
}
