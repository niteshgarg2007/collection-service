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
@Table(name="V_ITS_DISTRIBUTION_REVERSAL")
public class VITSDistributionReversalEO implements Serializable {
	
	private static final long serialVersionUID = -6005759042805830692L;
	
	@Id
	@Column(name="OVPDST_ID")
	private Long ovpdstId;
	
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="REVERSAL_DST_AMOUNT")
	private Double reversalDstAmount;
}
