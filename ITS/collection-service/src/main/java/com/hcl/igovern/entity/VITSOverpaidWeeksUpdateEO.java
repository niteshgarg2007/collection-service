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
@Table(name="V_ITS_OVERPAID_WEEKS_UPDATE")
public class VITSOverpaidWeeksUpdateEO implements Serializable {
	
	private static final long serialVersionUID = 3503536781876744816L;

	@Id
	@Column(name="WK_CERT_ID")
	private Long wkCertId;
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="CBWK_BWE_DT")
	private Timestamp cbwkBweDt;
	
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;
	
	@Column(name="PRGM_CD")
	private String prgmCd;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="RECOVERY_AMOUNT")
	private Double recoveryAmount;
}
