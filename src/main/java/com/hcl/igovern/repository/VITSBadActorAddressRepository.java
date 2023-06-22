package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSBadActorAddressEO;

@Repository
public interface VITSBadActorAddressRepository extends JpaRepository<VITSBadActorAddressEO, Long> {

}
