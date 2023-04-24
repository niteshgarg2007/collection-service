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
	private Timestamp cbwkBweDt;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="ENDST_PD_AMT")
	private Double endstPdAmt;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
}
