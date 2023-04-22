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
@Table(name="ITS_RECOVERY")
public class ItsRecoveryEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 6506400565285632547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="RECOVERY_DATE")
	private Timestamp recoveryDate;
	
	@Column(name="RECOVERY_STATUS")
	private String recoveryStatus;
	
	@Column(name="RECOVERY_EFF_DATE")
	private Timestamp recoveryEffDate;
	
	@Column(name="TOTAL_PAYMENT_AMOUNT")
	private Double totalPaymentAmount;
	
	@Column(name="TOTAL_RECOVERY_AMOUNT")
	private Double totalRecoveryAmount;
	
	@Column(name="ALL_OVP_IND")
	private String allOvpInd;
	
	@Column(name="REFUND_EXCESS_IND")
	private String refundExcessInd;
}