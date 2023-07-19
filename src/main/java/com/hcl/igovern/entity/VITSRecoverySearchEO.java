package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="V_ITS_RECOVERY_SEARCH")
public class VITSRecoverySearchEO implements Serializable {

	private static final long serialVersionUID = 4869336960288963379L;

	@Id
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="RECOVERY_DATE")
	private Timestamp recoveryDate;
	
	@Column(name="RECOVERY_EFF_DATE")
	private Timestamp recoveryEffDate;
	
	@Column(name="RECOVERY_STATUS")
	private String recoveryStatus;
	
	@Column(name="TOTAL_RECOVERY_AMOUNT")
	private Double totalRecoveryAmount;
	
	@Column(name="APPLIED_AMOUNT")
	private Double appliedAmount;
	
	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;
	
	@Column(name="BAD_ACTOR_NAME")
	private String badActorName;
	
	@Column(name="BAD_ACTOR_SSN")
	private String badActorSsn;
}
