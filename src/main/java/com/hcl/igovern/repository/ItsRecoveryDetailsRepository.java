package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsRecoveryDetailsEO;

@Repository
public interface ItsRecoveryDetailsRepository extends JpaRepository<ItsRecoveryDetailsEO, Long> {

	List<ItsRecoveryDetailsEO> findByOvpId(Long ovpId);

	List<ItsRecoveryDetailsEO> findByOvpIdAndVictimBadActorXrefId(Long ovpId, Long victimBadActorXrefId);

}
