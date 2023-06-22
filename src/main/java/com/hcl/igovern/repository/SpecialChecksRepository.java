package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.SpecialChecksEO;

@Repository
public interface SpecialChecksRepository extends JpaRepository<SpecialChecksEO, Long> {

}
