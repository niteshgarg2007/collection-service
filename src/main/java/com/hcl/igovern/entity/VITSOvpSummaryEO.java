package com.hcl.igovern.entity;

import java.io.Serializable;

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
@Table(name="V_ITS_OVP_SUMMARY")
public class VITSOvpSummaryEO implements Serializable {
	
	private static final long serialVersionUID = 7531845130872645099L;
	
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
	
	@Column(name="RECOVERY_AMOUNT")
	private Double recoveryAmount;
	
	@Column(name="RESTI_AMOUNT")
	private Double restiAmount;
	
	@Column(name="OVP_TOTAL")
	private Double ovpTotal;
	
	@Column(name="OVP_BALANCE")
	private Double ovpBalance;
	
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
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="BAD_ACTOR_SSN")
	private String badActorSsn;
	
	@Column(name="PRTY_TAX_ID")
	private String prtyTaxId;
}
