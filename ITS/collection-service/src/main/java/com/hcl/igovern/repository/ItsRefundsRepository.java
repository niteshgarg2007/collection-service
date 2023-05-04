package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsRefundsEO;

@Repository
public interface ItsRefundsRepository extends JpaRepository<ItsRefundsEO, Long> {
	
}
