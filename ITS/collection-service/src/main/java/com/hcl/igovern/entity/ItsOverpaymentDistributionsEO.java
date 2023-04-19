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
@Table(name="ITS_OVERPAYMENT_DISTRIBUTIONS")
public class ItsOverpaymentDistributionsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -8731537801947223602L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OVPDST_ID")
	private Long ovpdstId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="OVPDTLS_ID")
	private ItsOverpaymentDetailsEO ovpdtlsId;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="OVPDST_AMOUNT")
	private Double ovpdstAmount;

	public Long getOvpdstId() {
		return ovpdstId;
	}

	public void setOvpdstId(Long ovpdstId) {
		this.ovpdstId = ovpdstId;
	}

	public ItsOverpaymentDetailsEO getOvpdtlsId() {
		return ovpdtlsId;
	}

	public void setOvpdtlsId(ItsOverpaymentDetailsEO ovpdtlsId) {
		this.ovpdtlsId = ovpdtlsId;
	}

	public String getPrgfndAcctNo() {
		return prgfndAcctNo;
	}

	public void setPrgfndAcctNo(String prgfndAcctNo) {
		this.prgfndAcctNo = prgfndAcctNo;
	}

	public Double getOvpdstAmount() {
		return ovpdstAmount;
	}

	public void setOvpdstAmount(Double ovpdstAmount) {
		this.ovpdstAmount = ovpdstAmount;
	}
}
