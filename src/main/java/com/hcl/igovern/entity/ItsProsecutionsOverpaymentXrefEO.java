package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_PROSECUTIONS_OVERPAYMENT_XREF")
public class ItsProsecutionsOverpaymentXrefEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -3537133555236280792L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROS_OVP_XREF_ID")
	private Long prosOvpXrefId;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="PROS_ID")
	private ItsProsecutionEO prosId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="RESI_AMT")
	private Double resiAmt;
	
	@Column(name="END_DT")
	private Timestamp endDt;
}
