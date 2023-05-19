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
@Table(name="V_ITS_OVP_RECV_DST_REVERSAL")
public class VITSOvpRecvDstReversalEO implements Serializable {

	private static final long serialVersionUID = -2967032589156076868L;
	
	@Id
	@Column(name="OVP_RECV_DST_ID")
	private Long ovpRecvDstId;
	
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="REVERSAL_RECDST_AMOUNT")
	private Double reversalRecdstAmount;
}
