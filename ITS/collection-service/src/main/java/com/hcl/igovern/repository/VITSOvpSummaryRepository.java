package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOvpSummaryEO;

@Repository
public interface VITSOvpSummaryRepository extends JpaRepository<VITSOvpSummaryEO, Long> {

	List<VITSOvpSummaryEO> findByVictimBadActorXrefId(Long victimBadActorXrefId);

	List<VITSOvpSummaryEO> findByBadActorId(Long badActorId);

	@Query(value = "SELECT * FROM V_ITS_OVP_SUMMARY WHERE BAD_ACTOR_ID=?1 AND (PROS_ID=?2 OR PROS_ID IS NULL)", nativeQuery = true)
	List<VITSOvpSummaryEO> getVITSProsecutionUpdateByBadActorList(Long badActorId, Long prosId);

	@Query(value = "SELECT * FROM V_ITS_OVP_SUMMARY WHERE VICTIM_BAD_ACTOR_XREF_ID=?1 AND (PROS_ID=?2 OR PROS_ID IS NULL)", nativeQuery = true)
	List<VITSOvpSummaryEO> getVITSProsecutionUpdateByXrefList(Long victimBadActorXrefId, Long prosId);

}
