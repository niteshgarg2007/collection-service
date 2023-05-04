package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsRecoveryEO;

@Repository
public interface ItsRecoveryRepository extends JpaRepository<ItsRecoveryEO, Long>{

}
