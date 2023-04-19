package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.VITSOverpaidWeeksEO;

@Repository
public interface VITSOverpaidWeeksRepository extends JpaRepository<VITSOverpaidWeeksEO, Long> {

}
