package com.hcl.igovern.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="V_ITS_OVERPAID_WEEKS")
public class VITSOverpaidWeeksEO implements Serializable {

	private static final long serialVersionUID = 5690637245007116487L;
	
	
	@Id
	@Column(name="WK_CERT_ID")
	private Long wkCertId;
	
	@Column(name="CLAIM_ID")
	private Long claimId;
	
	@Column(name="CBWK_BWE_DT")
	private String cbwkBweDt;
	
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;
	
	//@Column(name="RECOVERY_AMOUNT")
	//private Double recoveryAmount;
	
	@Column(name="PRGM_CD")
	private String prgmCd;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;

	public Long getWkCertId() {
		return wkCertId;
	}

	public void setWkCertId(Long wkCertId) {
		this.wkCertId = wkCertId;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getCbwkBweDt() {
		return cbwkBweDt;
	}

	public void setCbwkBweDt(String cbwkBweDt) {
		this.cbwkBweDt = cbwkBweDt;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPrgmCd() {
		return prgmCd;
	}

	public void setPrgmCd(String prgmCd) {
		this.prgmCd = prgmCd;
	}

	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}

	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}
}
