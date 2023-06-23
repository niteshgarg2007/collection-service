package com.hcl.igovern.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="CHECKS")
public class ChecksEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 3979559077191990949L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CHCK_ID")
	private Long chckId;

	@Column(name="PRTY_ID")
	private Long prtyId;
	
	@Column(name="RCVH_NO")
	private Long rcvhNo;
	
	@Column(name="REF_NO")
	private Long refNo;
	
	@Column(name="CHCK_NO")
	private String chckNo;
	
	@Column(name="CHCK_AMT")
	private Double chckAmt;
	
	@Column(name="CHCK_XTRCT_IND")
	private String chckXtrctInd;
	
	@Column(name="CHCK_GL_CHCK_STS_CD")
	private String chckGlChckStsCd;
	
	@Column(name="OVPHIS_PRTY_ID")
	private Long ovphisPrtyId;
	
	@Column(name="PYMT_TYP_CD")
	private String pymtTypCd;
	
	@Column(name="PRNT_CHCK_ID")
	private Long prntChckId;
}
