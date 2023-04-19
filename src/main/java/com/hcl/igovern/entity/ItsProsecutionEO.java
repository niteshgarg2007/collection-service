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
@Table(name="ITS_PROSECUTION")
public class ItsProsecutionEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 1731968749508062278L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROS_ID")
	private Long prosId;
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="CNTY_ID")
	private Long cntyId;
	
	@Column(name="PROS_VENUE_ID")
	private Long prosVenueId;
	
	@Column(name="CASE_NO")
	private String caseNo;
	
	@Column(name="WARRANT_REQ_DT")
	private Timestamp warrantReqDt;
	
	@Column(name="WARRANT_NO")
	private String warrantNo;
	
	@Column(name="INI_COURT_DT")
	private Timestamp iniCourtDt;
	
	@Column(name="BPC_PROS_DISP_CD")
	private String bpcProsDispCd;
	
	@Column(name="CONTINUED_DT")
	private Timestamp continuedDt;
	
	@Column(name="CONVICTION_DT")
	private Timestamp convictionDt;
	
	@Column(name="RESTITUTION_ORDERED_IND")
	private String restitutionOrderedInd;
	
	@Column(name="RESI_AMT")
	private Double resiAmt;
	
	@Column(name="SENTENCE")
	private String sentence;
	
	@Column(name="COMMENTS")
	private String comments;
}
