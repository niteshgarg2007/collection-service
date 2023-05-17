package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSRefundsDataVO {

	private Long refundId;
	private Long badActorId;
	private Double refAmt;
	private String dateCreated;
	private String refDspn;
	private Double refAmtIssued;
	private String refIssuedDate;	
}
