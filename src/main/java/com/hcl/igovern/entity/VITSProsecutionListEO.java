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
@Table(name = "V_ITS_PROSECUTION_LIST")
public class VITSProsecutionListEO implements Serializable {

	private static final long serialVersionUID = -5827505353270936247L;

	@Id 
	@Column(name="PROS_ID")
	private Long prosId;

	@Column(name = "BAD_ACTOR_ID")
	private Long badActorId;

	@Column(name = "PROS_VENUE")
	private String prosVenue;
	
	@Column(name = "CASE_NO")
	private String caseNo;
	
	@Column(name = "WARRANT_NO")
	private String warrantNo;
	
	@Column(name = "WARRANT_REQ_DT")
	private Timestamp warrantReqDt;
	
	@Column(name = "INI_COURT_DT")
	private Timestamp iniCourtDt;
	
	@Column(name = "PROS_DISP")
	private String prosDisp;
	
	@Column(name = "CONTINUED_DT")
	private Timestamp continuedDt;
	
	@Column(name = "CONVICTION_DT")
	private Timestamp convictionDt;
	
	@Column(name = "RESI_AMT")
	private Double resiAmt;
	
	@Column(name = "DATE_CREATED")
	private Timestamp dateCreated;
	
	@Column(name = "BAD_ACTOR_SSN")
	private String badActorSsn;
	
	@Column(name = "BAD_ACTOR_NAME")
	private String badActorName;

}
