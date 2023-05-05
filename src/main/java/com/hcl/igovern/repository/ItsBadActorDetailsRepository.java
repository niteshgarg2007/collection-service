package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsBadActorDetailsEO;

@Repository
public interface ItsBadActorDetailsRepository extends JpaRepository<ItsBadActorDetailsEO, Long> {
	
}
