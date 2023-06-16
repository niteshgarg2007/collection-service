package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsProsecutionsOverpaymentXrefEO;

@Repository
public interface ItsProsecutionsOverpaymentXrefRepository extends JpaRepository<ItsProsecutionsOverpaymentXrefEO, Long> {

}
