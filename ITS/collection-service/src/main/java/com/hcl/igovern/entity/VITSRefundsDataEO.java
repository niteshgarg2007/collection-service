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
@Table(name="V_ITS_REFUNDS_DATA")
public class VITSRefundsDataEO implements Serializable {
	
	private static final long serialVersionUID = -9056719027457291144L;
	
	@Id
	@Column(name="REFUND_ID")
	private Long refundId;
	
	@Column(name="BAD_ACTOR_ID")
	private Long badActorId;
	
	@Column(name="REF_AMT")
	private Double refAmt;
	
	@Column(name="DATE_CREATED")
	private Timestamp dateCreated;
	
	@Column(name="REF_DSPN")
	private String refDspn;
	
	@Column(name="REF_AMT_ISSUED")
	private Double refAmtIssued;
	
	@Column(name="REF_ISSUED_DATE")
	private Timestamp refIssuedDate;

}
