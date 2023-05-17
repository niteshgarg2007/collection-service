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
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
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
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "OVP_ID")  
	private ItsOverpaymentEO itsOverpayment;
	
	@OneToMany(targetEntity = ItsOverpaymentDistributionsEO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ovpdtlsId", fetch=FetchType.EAGER)
	private List<ItsOverpaymentDistributionsEO> itsOverpaymentDstDtls;
	
	@OneToMany(targetEntity = ItsOverpaymentTransactionsEO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ovpdtlsId", fetch=FetchType.EAGER)
	private List<ItsOverpaymentTransactionsEO> itsOverpaymentTranDtls;
	
	@Transient
	private Double ovpAmt;
}
