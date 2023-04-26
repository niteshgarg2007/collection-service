package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="V_ITS_OVP_DISTRIBUTION_EXISTING")
public class VITSOvpDistributionExistingEO implements Serializable {

	private static final long serialVersionUID = 7847862553810069582L;
	
	@Id
	@Column(name="OVPDST_ID")
	private Long ovpdstId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="CBWK_BWE_DT")
	private Timestamp cbwkBweDt;
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
}
