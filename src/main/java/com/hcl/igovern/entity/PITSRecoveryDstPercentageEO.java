package com.hcl.igovern.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@NamedStoredProcedureQuery(
    name = "P_ITS_RECOVERY_DST_PERCENTAGE",
    procedureName = "P_ITS_RECOVERY_DST_PERCENTAGE",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "OVPDTLS_ID", type = Long.class)           
    },
    resultClasses = {PITSRecoveryDstPercentageEO.class}
)

public class PITSRecoveryDstPercentageEO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "SEQ_NO")
	private Long seqNo;
	
	@Column(name = "OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name = "PRGFND_ACCT_NO")  
	private String prgfndAcctNo;
	
	@Column(name = "OVPDST_AMOUNT")  
	private Double ovpdstAmount;
	
	@Column(name = "MON_PERCENTAGE")  
	private Double monPercentage;
}
