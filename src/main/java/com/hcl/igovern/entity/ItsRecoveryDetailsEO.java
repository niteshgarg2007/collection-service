package com.hcl.igovern.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="ITS_RECOVERY_DETAILS")
public class ItsRecoveryDetailsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -616272068321622393L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RECOVERY_DTLS_ID")
	private Long recoveryDtlsId;
	
	@Column(name="RECOVERY_ID")
	private Long recoveryId;
	
	@Column(name="VICTIM_BAD_ACTOR_XREF_ID")
	private Long victimBadActorXrefId;
	
	@Column(name="OVP_ID")
	private Long ovpId;
	
	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;
	
	@Column(name="COMMENT")
	private String comment;
	
	@OneToMany(targetEntity = ItsOverpaymentTransactionsEO.class, cascade = CascadeType.ALL, mappedBy = "recoveryDtlsId", fetch=FetchType.EAGER)
	@JsonIgnoreProperties({"recoveryDtlsId"})
	private List<ItsOverpaymentTransactionsEO> itsOverpaymentTranDtls;

}
