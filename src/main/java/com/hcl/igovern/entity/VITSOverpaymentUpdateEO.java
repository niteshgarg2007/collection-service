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
}
