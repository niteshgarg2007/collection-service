package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOvpSummaryEO;

@Repository
public interface VITSOvpSummaryRepository extends JpaRepository<VITSOvpSummaryEO, Long> {

	List<VITSOvpSummaryEO> findByVictimBadActorXrefId(Long victimBadActorXrefId);

	List<VITSOvpSummaryEO> findByBadActorId(Long badActorId);

}
