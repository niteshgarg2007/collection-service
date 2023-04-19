package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_OVERPAYMENT_DETAILS")
public class ItsOverpaymentDetailsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 6739297778958776345L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="OVPTYP_CD")
	private String ovptypCd;
	
	@Column(name="CBWK_BWE_DT")
	private Timestamp cbwkBweDt;
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="ADD_COMP_PRG_DTL_ID")
	private Long addCompPrgDtlId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "OVP_ID")  
	private ItsOverpaymentEO itsOverpayment;
	
	@OneToMany(targetEntity = ItsOverpaymentDistributionsEO.class, cascade = CascadeType.ALL, mappedBy = "ovpdtlsId", fetch=FetchType.EAGER)
	private List<ItsOverpaymentDistributionsEO> itsOverpaymentDstDtls;
	
	@OneToMany(targetEntity = ItsOverpaymentTransactionsEO.class, cascade = CascadeType.ALL, mappedBy = "ovpdtlsId", fetch=FetchType.EAGER)
	private List<ItsOverpaymentTransactionsEO> itsOverpaymentTranDtls;

	public Long getOvpdtlsId() {
		return ovpdtlsId;
	}

	public void setOvpdtlsId(Long ovpdtlsId) {
		this.ovpdtlsId = ovpdtlsId;
	}

	public String getOvptypCd() {
		return ovptypCd;
	}

	public void setOvptypCd(String ovptypCd) {
		this.ovptypCd = ovptypCd;
	}

	public Timestamp getCbwkBweDt() {
		return cbwkBweDt;
	}

	public void setCbwkBweDt(Timestamp cbwkBweDt) {
		this.cbwkBweDt = cbwkBweDt;
	}

	public Long getClmId() {
		return clmId;
	}

	public void setClmId(Long clmId) {
		this.clmId = clmId;
	}

	public Long getAddCompPrgDtlId() {
		return addCompPrgDtlId;
	}

	public void setAddCompPrgDtlId(Long addCompPrgDtlId) {
		this.addCompPrgDtlId = addCompPrgDtlId;
	}

	public ItsOverpaymentEO getItsOverpayment() {
		return itsOverpayment;
	}

	public void setItsOverpayment(ItsOverpaymentEO itsOverpayment) {
		this.itsOverpayment = itsOverpayment;
	}

	public List<ItsOverpaymentDistributionsEO> getItsOverpaymentDstDtls() {
		return itsOverpaymentDstDtls;
	}

	public void setItsOverpaymentDstDtls(List<ItsOverpaymentDistributionsEO> itsOverpaymentDstDtls) {
		this.itsOverpaymentDstDtls = itsOverpaymentDstDtls;
	}

	public List<ItsOverpaymentTransactionsEO> getItsOverpaymentTranDtls() {
		return itsOverpaymentTranDtls;
	}

	public void setItsOverpaymentTranDtls(List<ItsOverpaymentTransactionsEO> itsOverpaymentTranDtls) {
		this.itsOverpaymentTranDtls = itsOverpaymentTranDtls;
	}

}
