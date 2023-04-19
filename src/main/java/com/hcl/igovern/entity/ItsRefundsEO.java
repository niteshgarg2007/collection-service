package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_REFUNDS")
public class ItsRefundsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 5266323773910544898L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="REFUND_ID")
	private Long refundId;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="REF_AMT")
	private Double refAmt;
	
	@Column(name="REF_DSPN")
	private String refDspn;
	
	@Column(name="REF_AMT_ISSUED")
	private Double refAmtIssued;
	
	@Column(name="REF_ISSUED_DATE")
	private Timestamp refIssuedDate;
}
