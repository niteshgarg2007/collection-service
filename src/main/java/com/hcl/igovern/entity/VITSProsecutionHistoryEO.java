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
@Table(name = "V_ITS_PROSECUTION_HISTORY")
public class VITSProsecutionHistoryEO implements Serializable {

	private static final long serialVersionUID = 3364659390528203839L;
	
	@Id 
	@Column(name="PROSHIST_ID")
	private Long proshistId;
	
	@Column(name="PROS_ID")
	private Long prosId;

	@Column(name = "PROS_VENUE")
	private String prosVenue;
	
	@Column(name = "CASE_NO")
	private String caseNo;
	
	@Column(name = "WARRANT_NO")
	private String warrantNo;
	
	@Column(name = "PROS_DISP")
	private String prosDisp;
	
	@Column(name="RESI_AMT_TOT")
	private Double resiAmtTot;
	
	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name="DATE_MODIFIED")
	private Timestamp dateModified;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
}
