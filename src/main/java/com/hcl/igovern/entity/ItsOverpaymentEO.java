package com.hcl.igovern.entity;

import java.io.Serializable;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_OVERPAYMENT")
public class ItsOverpaymentEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 8679288140303137104L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVPDIS_CD")
	private String ovpdisCd;
	
	@Column(name="OVPCLS_CD")
	private String ovpclsCd;
	
	@Column(name="OVPSTS_CD")
	private String ovpstsCd;
	
	@Column(name="OVPORG_CD")
	private String ovporgCd;
	
	@Column(name="OVPDORG_CD")
	private String ovpdorgCd;
	
	@OneToMany(targetEntity = ItsOverpaymentDetailsEO.class, cascade = CascadeType.ALL, mappedBy = "itsOverpayment", fetch=FetchType.EAGER)
	private List<ItsOverpaymentDetailsEO> itsOverpaymentDtls;

	public Long getOvpId() {
		return ovpId;
	}

	public void setOvpId(Long ovpId) {
		this.ovpId = ovpId;
	}

	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}

	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}

	public String getOvpdisCd() {
		return ovpdisCd;
	}

	public void setOvpdisCd(String ovpdisCd) {
		this.ovpdisCd = ovpdisCd;
	}

	public String getOvpclsCd() {
		return ovpclsCd;
	}

	public void setOvpclsCd(String ovpclsCd) {
		this.ovpclsCd = ovpclsCd;
	}

	public String getOvpstsCd() {
		return ovpstsCd;
	}

	public void setOvpstsCd(String ovpstsCd) {
		this.ovpstsCd = ovpstsCd;
	}

	public String getOvporgCd() {
		return ovporgCd;
	}

	public void setOvporgCd(String ovporgCd) {
		this.ovporgCd = ovporgCd;
	}

	public String getOvpdorgCd() {
		return ovpdorgCd;
	}

	public void setOvpdorgCd(String ovpdorgCd) {
		this.ovpdorgCd = ovpdorgCd;
	}

	public List<ItsOverpaymentDetailsEO> getItsOverpaymentDtls() {
		return itsOverpaymentDtls;
	}

	public void setItsOverpaymentDtls(List<ItsOverpaymentDetailsEO> itsOverpaymentDtls) {
		this.itsOverpaymentDtls = itsOverpaymentDtls;
	}

}
