package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsRecoveryDetailsEO;

@Repository
public interface ItsRecoveryDetailsRepository extends JpaRepository<ItsRecoveryDetailsEO, Long> {

}
