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
@Table(name="V_ITS_OVERPAYMENT_DETAILS")
public class VITSOverpaymentDetailsEO implements Serializable {

	private static final long serialVersionUID = -5098799479785131510L;
	
	@Id
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="OVPTYP_CD")
	private String ovptypCd;
	
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
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="ADD_COMP_PRG_DTL_ID")
	private Long addCompPrgDtlId;
	
	@Column(name="CBWK_BWE_DT")
	private Timestamp cbwkBweDt;
	
	@Column(name="OVP_AMT")
	private Double ovpAmt;
	
	@Column(name="RECOVERY_AMT")
	private Double recoveryAmt;
}
