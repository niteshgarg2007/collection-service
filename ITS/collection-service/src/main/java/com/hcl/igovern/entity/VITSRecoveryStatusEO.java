package com.hcl.igovern.entity;

import java.io.Serializable;

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
@Table(name="V_ITS_RECOVERY_STATUS")
public class VITSRecoveryStatusEO implements Serializable{
	
	private static final long serialVersionUID = -6558002620487739104L;
	
	@Id
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="OVP_ID")
	private Double ovpId;
	
	@Column(name="RECOVERY_STATUS")
	private String recoveryStatus;
}
