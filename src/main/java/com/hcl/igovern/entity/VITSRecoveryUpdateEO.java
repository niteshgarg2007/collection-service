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
@Table(name="V_ITS_RECOVERY_UPDATE")
public class VITSRecoveryUpdateEO implements Serializable {

	private static final long serialVersionUID = 6114274173365478633L;
	
	@Id
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="BAD_ACTOR_SSN")
	private String badActorSsn;
	
	@Column(name="PRTY_TAX_ID")
	private String prtyTaxId;
	
	@Column(name="OVP_TOTAL")
	private Double ovpTotal;
	
	@Column(name="OVP_BALANCE")
	private Double ovpBalance;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="RECOVERY_DATE")
	private Timestamp recoveryDate;
	
	@Column(name="RECOVERY_STATUS")
	private String recoveryStatus;
	
	@Column(name="RECOVERY_EFF_DATE")
	private Timestamp recoveryEffDate;
	
	@Column(name="TOTAL_PAYMENT_AMOUNT")
	private Double totalPaymentAmount;
	
	@Column(name="TOTAL_RECOVERY_AMOUNT")
	private Double totalRecoveryAmount;
	
	@Column(name="ALL_OVP_IND")
	private String allOvpInd;
	
	@Column(name="REFUND_EXCESS_IND")
	private String refundExcessId;
	
	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;
	
	@Column(name="COMMENT")
	private String comment;

}
