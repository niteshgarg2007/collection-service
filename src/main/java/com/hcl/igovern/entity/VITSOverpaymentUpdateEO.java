package com.hcl.igovern.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="V_ITS_OVERPAYMENT_UPDATE")
public class VITSOverpaymentUpdateEO implements Serializable {

	private static final long serialVersionUID = 2159132328949422117L;

	@Id
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVP_AMOUNT")
	private Double ovpAmount;
	
	@Column(name="OVP_INTEREST")
	private Double ovpInterest;
	
	@Column(name="OVP_PENALTY")
	private Double ovpPenalty;
	
	@Column(name="OVP_COC")
	private Double ovpCoc;
	
	@Column(name="OVPSTS_CD")
	private String ovpstsCd;
	
	@Column(name="OVPDIS_CD")
	private String ovpdisCd;
	
	@Column(name="OVPCLS_CD")
	private String ovpclsCd;
	
	@Column(name="OVPORG_CD")
	private String ovporgCd;
	
	@Column(name="OVPDORG_CD")
	private String ovpdorgCd;
	
	@Column(name="DATE_CREATED")
	private String dateCreated;

	public Long getOvpId() {
		return ovpId;
	}

	public void setOvpId(Long ovpId) {
		this.ovpId = ovpId;
	}

	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}

	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}

	public Double getOvpAmount() {
		return ovpAmount;
	}

	public void setOvpAmount(Double ovpAmount) {
		this.ovpAmount = ovpAmount;
	}

	public Double getOvpInterest() {
		return ovpInterest;
	}

	public void setOvpInterest(Double ovpInterest) {
		this.ovpInterest = ovpInterest;
	}

	public Double getOvpPenalty() {
		return ovpPenalty;
	}

	public void setOvpPenalty(Double ovpPenalty) {
		this.ovpPenalty = ovpPenalty;
	}

	public Double getOvpCoc() {
		return ovpCoc;
	}

	public void setOvpCoc(Double ovpCoc) {
		this.ovpCoc = ovpCoc;
	}

	public String getOvpstsCd() {
		return ovpstsCd;
	}

	public void setOvpstsCd(String ovpstsCd) {
		this.ovpstsCd = ovpstsCd;
	}

	public String getOvpdisCd() {
		return ovpdisCd;
	}

	public void setOvpdisCd(String ovpdisCd) {
		this.ovpdisCd = ovpdisCd;
	}

	public String getOvpclsCd() {
		return ovpclsCd;
	}

	public void setOvpclsCd(String ovpclsCd) {
		this.ovpclsCd = ovpclsCd;
	}

	public String getOvporgCd() {
		return ovporgCd;
	}

	public void setOvporgCd(String ovporgCd) {
		this.ovporgCd = ovporgCd;
	}

	public String getOvpdorgCd() {
		return ovpdorgCd;
	}

	public void setOvpdorgCd(String ovpdorgCd) {
		this.ovpdorgCd = ovpdorgCd;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
}
