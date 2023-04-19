package com.hcl.igovern.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="V_ITS_OVP_DISTRIBUTION")
public class VITSOvpDistributionEO implements Serializable {

	private static final long serialVersionUID = -7326013465912083308L;
	
	@Id
	@Column(name="WP_DIST_ID")
	private Long wpDistId;
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="PRGM_CD")
	private String prgmCd;
	
	@Column(name="CBWK_BWE_DT")
	private String cbwkBweDt;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="ENDST_PD_AMT")
	private Double endstPdAmt;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;

	public Long getWpDistId() {
		return wpDistId;
	}

	public void setWpDistId(Long wpDistId) {
		this.wpDistId = wpDistId;
	}

	public Long getClmId() {
		return clmId;
	}

	public void setClmId(Long clmId) {
		this.clmId = clmId;
	}

	public String getPrgmCd() {
		return prgmCd;
	}

	public void setPrgmCd(String prgmCd) {
		this.prgmCd = prgmCd;
	}

	public String getCbwkBweDt() {
		return cbwkBweDt;
	}

	public void setCbwkBweDt(String cbwkBweDt) {
		this.cbwkBweDt = cbwkBweDt;
	}

	public String getPrgfndAcctNo() {
		return prgfndAcctNo;
	}

	public void setPrgfndAcctNo(String prgfndAcctNo) {
		this.prgfndAcctNo = prgfndAcctNo;
	}

	public Double getEndstPdAmt() {
		return endstPdAmt;
	}

	public void setEndstPdAmt(Double endstPdAmt) {
		this.endstPdAmt = endstPdAmt;
	}

	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}

	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}
}
