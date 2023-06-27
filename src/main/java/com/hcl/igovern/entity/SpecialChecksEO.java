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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="SPECIAL_CHECKS")
public class SpecialChecksEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -2969737208223898837L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SPL_CHCK_ID")
	private Long splChckId;
	
	@Column(name="CLM_ID")
	private Long clmId;
	
	@Column(name="PRTY_ID")
	private Long prtyId;
	
	@Column(name="CPROL_CD")
	private String cprolCd;
	
	@Column(name="SPL_CHCK_NO")
	private String splChckNo;
	
	@Column(name="PAY_TO_ORDR_OF")
	private String payToOrderOf;
	
	@Column(name="ADDR_LINE_1")
	private String addrLine1;
	
	@Column(name="ADDR_LINE_2")
	private String addrLine2;
	
	@Column(name="CITY_NM")
	private String cityNm;
	
	@Column(name="STT_CD")
	private String sttCd;
	
	@Column(name="ZIP_CD")
	private String zipCd;
	
	@Column(name="ZIP_PLUS_FOUR")
	private String zipPlusFour;
	
	@Column(name="CHK_AMT")
	private Double chkAmt;
	
	@Column(name="REASONS")
	private String reasons;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@Column(name="GLCTRL_SUB_CD")
	private String glctrlSubCd;
	
	@Column(name="ITS_REFUND_ID")
	private Long itsRefundId;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "CHCK_ID")  
	private ChecksEO checksEO;
}
