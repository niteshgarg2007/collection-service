package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsVictimBadActorXrefEO;

@Repository
public interface ItsVictimBadActorXrefRepository extends JpaRepository<ItsVictimBadActorXrefEO, Long> {

	ItsVictimBadActorXrefEO findByBadActorIdAndClmtDtlId(Long badActorId, Long clmtDtlId);
	
}
