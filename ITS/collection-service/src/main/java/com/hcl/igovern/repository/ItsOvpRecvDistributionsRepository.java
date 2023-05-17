package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsOvpRecvDistributionsEO;

@Repository
public interface ItsOvpRecvDistributionsRepository extends JpaRepository<ItsOvpRecvDistributionsEO, Long> {
	
}
