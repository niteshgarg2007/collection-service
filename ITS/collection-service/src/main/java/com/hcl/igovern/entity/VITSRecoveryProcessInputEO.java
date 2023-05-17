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
@Table(name="V_ITS_RECOVERY_PROCESS_INPUT")
public class VITSRecoveryProcessInputEO implements Serializable {

	private static final long serialVersionUID = 3549685115142065851L;
	
	@Id
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="OVPTYP_CD")
	private String ovptypCd;
	
	@Column(name="CBWK_BWE_DT")
	private Timestamp cbwkBweDt;
	
	@Column(name="OVP_BALANCE")
	private Double ovpBalance;
}
