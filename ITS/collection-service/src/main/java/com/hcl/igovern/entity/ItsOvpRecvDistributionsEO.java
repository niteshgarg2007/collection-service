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
@Table(name="ITS_OVP_RECV_DISTRIBUTIONS")
public class ItsOvpRecvDistributionsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -7210661931618582731L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OVP_RECV_DST_ID")
	private Long ovpRecvDstId;
	
	@Column(name="OVPDTLS_ID")
	private Long ovpdtlsId;
	
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="RECDST_AMOUNT")
	private Double recdstAmount;
}
