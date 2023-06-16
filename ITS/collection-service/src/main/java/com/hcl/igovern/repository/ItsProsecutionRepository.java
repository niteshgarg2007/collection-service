package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsProsecutionEO;

@Repository
public interface ItsProsecutionRepository extends JpaRepository<ItsProsecutionEO, Long> {

}
