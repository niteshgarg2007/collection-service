package com.hcl.igovern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsOverpaymentTransactionsEO;

@Repository
public interface OverpaymentTransactionsRepository extends JpaRepository<ItsOverpaymentTransactionsEO, Long> {

}
