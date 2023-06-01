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
@Table(name="V_ITS_RECOVSEARCH_DETAILS")
public class VITSRecovsearchDetailsEO implements Serializable {

	private static final long serialVersionUID = 6443623048185440956L;
	
	@Id
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="BAD_ACTOR_SSN")
	private String badActorSsn;
	
	@Column(name="PRTY_TAX_ID")
	private String prtyTaxId;

}
