package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;

@Repository
public interface VITSOverpaidWeeksUpdateRepository extends JpaRepository<VITSOverpaidWeeksUpdateEO, Long> {

	List<VITSOverpaidWeeksUpdateEO> findByVictimBadActorXrefIdAndClmIdOrderByCbwkBweDt(Long victimBadActorXrefId, Long inputClaimId);

	List<VITSOverpaidWeeksUpdateEO> findByVictimBadActorXrefIdAndClmIdAndOvpIdOrderByCbwkBweDt(Long victimBadActorXrefId, Long inputClaimId, Long ovpId);

}
