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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_RECOVERY_HISTORY")
public class ITSRecoveryHistoryEO extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -2868132421120209153L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RECOVERY_HIST_ID")
	private Long recoveryHistId;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="RECOVERY_DATE")
	private Timestamp recoveryDate;
	
	@Column(name="RECOVERY_STATUS")
	private String recoveryStatus;
	
	@Column(name="TOTAL_RECOVERY_AMOUNT")
	private Double totalRecoveryAmount;

}
