package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsClaimantDetailsEO;

@Repository
public interface ItsClaimantDetailsRepository extends JpaRepository<ItsClaimantDetailsEO, Long> {

	ItsClaimantDetailsEO findByPrtyTaxId(String originalSsn);

}
