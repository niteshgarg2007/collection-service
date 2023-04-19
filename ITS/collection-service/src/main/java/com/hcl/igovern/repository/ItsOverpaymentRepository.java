package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsOverpaymentEO;

@Repository
public interface ItsOverpaymentRepository extends JpaRepository<ItsOverpaymentEO, Long> {

}
