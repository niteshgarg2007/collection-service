package com.hcl.igovern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.igovern.entity.ItsOverpaymentDistributionsEO;

@Repository
public interface OverpaymentDistributionsRepository extends JpaRepository<ItsOverpaymentDistributionsEO, Long> {

	List<ItsOverpaymentDistributionsEO> findByOvpdtlsId(Long ovpdtlsId);

}
