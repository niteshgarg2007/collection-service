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
@Table(name="V_ITS_TRANSACTION_REVERSAL")
public class VITSTransactionReversalEO implements Serializable {
	
	private static final long serialVersionUID = 7127882068649042400L;
	
	@Id
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="REVERSAL_TRANS_AMOUNT")
	private Double reversalTransAmount;
}
