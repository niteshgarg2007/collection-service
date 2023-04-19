package com.hcl.igovern.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_OVERPAYMENT_TRANSACTIONS")
public class ItsOverpaymentTransactionsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 8634059542927796952L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OVPTRANS_ID")
	private Long ovptransId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="OVPDTLS_ID")
	private ItsOverpaymentDetailsEO ovpdtlsId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="RECOVERY_DTLS_ID")
	private ItsRecoveryDetailsEO recoveryDtlsId;
	
	@Column(name="TRANS_AMOUNT")
	private Double transAmount;

	public Long getOvptransId() {
		return ovptransId;
	}

	public void setOvptransId(Long ovptransId) {
		this.ovptransId = ovptransId;
	}

	public ItsOverpaymentDetailsEO getOvpdtlsId() {
		return ovpdtlsId;
	}

	public void setOvpdtlsId(ItsOverpaymentDetailsEO ovpdtlsId) {
		this.ovpdtlsId = ovpdtlsId;
	}

	public ItsRecoveryDetailsEO getRecoveryDtlsId() {
		return recoveryDtlsId;
	}

	public void setRecoveryDtlsId(ItsRecoveryDetailsEO recoveryDtlsId) {
		this.recoveryDtlsId = recoveryDtlsId;
	}

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
}
